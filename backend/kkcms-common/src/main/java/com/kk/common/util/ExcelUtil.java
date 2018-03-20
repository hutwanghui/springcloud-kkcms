package com.kk.common.util;

import jxl.*;
import jxl.biff.CellReferenceHelper;
import jxl.biff.formula.FormulaException;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>Description: Excel工具类</p>
 */
public final class ExcelUtil {

    private static final Logger LOGGER = Logger.getLogger(ExcelUtil.class);

    public static void excelGenerationByTemplate(final File template, final File outputExcel, final Map dataSet) {
        excelGenerationByTemplate(template, outputExcel, dataSet, false);
    }

    public static void excelGenerationByTemplate(final File template, final File outputExcel, final Map dataSet,
                                                 final boolean protect) {
        excelGenerationByTemplate(template, outputExcel, dataSet, protect, DateUtil.TIME_PATTERN_DAY);
    }

    public static void excelGenerationByTemplate(final File template, final File outputExcel, Map dataSet,
                                                 final boolean protect, final String dateFormat) {
        try {
            //add by suntian to remove empty list to fix the bug of sum value mismatch rows
            Map dataSetBak = new HashMap();
            dataSetBak.putAll(dataSet);
            Set dataKeySet = dataSet.keySet();
            Iterator di = dataKeySet.iterator();
            String className = "";
            while (di.hasNext()) {
                Object content = new Object();
                content = di.next();
                try {
                    className = (dataSet.get(content)).getClass().getName();
                } catch (Exception e) {
                    continue;
                }
                if (className.equals("java.util.ArrayList") || className.equals("java.util.List") || className.equals("java.util.LinkedList")) {
                    if (((List) dataSet.get(content)).isEmpty()) {
                        dataSetBak.remove(content);
                    }
                }
            }
            dataSet = dataSetBak;
            Workbook templateWorkbook = Workbook.getWorkbook(template);//得到模版workbook
            WritableWorkbook outputWorkbook = Workbook.createWorkbook(outputExcel, templateWorkbook);//创建需生成的workbook
            int subTotalCol = -1;//记录小计的列,以防止重写模版行的时候覆盖小计第一行的数据
            for (int sheetNumber = 0; sheetNumber < outputWorkbook.getNumberOfSheets(); sheetNumber++) {//遍历生成的workbook的sheet
                WritableSheet sheet = outputWorkbook.getSheet(sheetNumber);//根据序号得到sheet
                Cell[] rowCells;
                Cell cell;
                formatFormulaCell(templateWorkbook.getSheet(sheetNumber), sheet);
                for (int row = 0; row < sheet.getRows(); row++) {//遍历sheet中的每一行
                    rowCells = sheet.getRow(row);//根据序号得到row
                    Range[] ranges = sheet.getMergedCells();//得到被合并的单元格集合(类Range的数组)
                    boolean expanded = false;
                    int expandCount = -1;
                    int subFieldColumn = -1;
                    int subGroupColumn = -1;
                    int subIdColumn = -1;
                    int subDigit = 2;
                    int subRow = row;
                    int lastRow = row - 1;
                    String lastCellCotents = null;
                    String cellContents = null;
                    String value = null;
                    double subTotal = 0;
                    for (int column = 0; column < rowCells.length; column++) {//遍历一行的中的每一列
                        cell = rowCells[column];//根据序号得到单元格
                        if (cell.getType().equals(CellType.LABEL)) {//单元格的类型(EXCEL中的定义)
                            Label label = (Label) cell;//转换为Label
                            String str = label.getString();
                            String type = "label";
                            // token analyse
                            Reader reader = new StringReader(str);
                            char c;
                            int i;
                            StringBuffer sb = new StringBuffer();
                            while ((i = reader.read()) != -1) {
                                c = (char) i;
                                if (c == '{') {//模版设定如{s1.aac01}
                                    StringBuffer buf = new StringBuffer();
                                    //读取一个单元格的模版设置
                                    int j = reader.read();
                                    while ((c = (char) j) != '}' && j != -1) {
                                        buf.append(c);
                                        j = reader.read();
                                    }
                                    int subIdx = buf.indexOf("#subfield");
                                    if (subIdx != -1) {
                                        subFieldColumn = column;
                                        buf = buf.delete(subIdx, subIdx + 9);
                                        subIdx = buf.indexOf("~number");
                                        if (subIdx != -1)
                                            try {
                                                subDigit = Integer.parseInt(buf.substring(subIdx + 7, subIdx + 8));
                                            } catch (Exception e) {
                                                /** ignore it */
                                            }
                                    }
                                    subIdx = buf.indexOf("#groupfield");
                                    if (subIdx != -1) {
                                        subGroupColumn = column;
                                        buf = buf.delete(subIdx, subIdx + 11);
                                    }
                                    String token = buf.toString();//s1.akc087~number2
                                    int typeIdx = token.indexOf('~');//取~number等模版设置
                                    if (typeIdx != -1 && typeIdx < (token.length() - 1)) {
                                        type = token.substring(typeIdx + 1);//type number2
                                        token = token.substring(0, typeIdx);//token s1.akc087+4
                                    }
                                    int dotIdx = token.indexOf('.');
                                    if (dotIdx == -1) {//如果没有s1.
                                        // result
                                        sb.append(getValue(dataSet.get(token), dateFormat));//不懂
                                    } else if (dotIdx < (token.length() - 1)) {
                                        // resultset
                                        String rsName = token.substring(0, dotIdx);//rsNmae s1
                                        String attStr = token.substring(dotIdx + 1);//attStr akc087+4
                                        if (attStr.equals("#subid")) {
                                            subIdColumn = column;
                                            continue;
                                        }
                                        List v = (List) dataSet.get(rsName);//取出s1这个List
                                        if (v != null) {
                                            int addIdx = attStr.indexOf('+');//判断有无+
                                            if (addIdx == -1) {
                                                if (v.size() > 0) {
                                                    Map record = (Map) v.get(0);
                                                    sb.append(getValue(record.get(attStr), dateFormat));
                                                }
                                            } else if (addIdx == (attStr.length() - 1)) {//有+要自动扩展
                                                attStr = attStr.substring(0, addIdx);
                                                if (!expanded) {
                                                    expandRow(sheet, row, v.size(), v.size());
                                                    expanded = true;
                                                }
                                                int subIdVal = 0;
                                                for (int m = 0; m < v.size(); m++) {
                                                    Map record = (Map) v.get(m);
                                                    if (subFieldColumn != -1 && attStr.equals("#subtotal")) {
                                                        subTotalCol = column;//记录小计的列,以防止重写模版行的时候覆盖小计第一行的数据
                                                        Cell ce = sheet.getCell(column, row);
                                                        cellContents = sheet.getRow(subRow)[subGroupColumn].getContents();
                                                        if (lastCellCotents == null)
                                                            lastCellCotents = cellContents;
                                                        if (cellContents != null && lastCellCotents != null && !lastCellCotents.equals(cellContents)) {
                                                            sheet.mergeCells(column, lastRow + 1, column, subRow - 1);//合计小计的单元格
                                                            addCell(sheet, column, lastRow + 1, subTotal, ce.getCellFormat(), subDigit);
                                                            if (subIdColumn != -1) {
                                                                sheet.mergeCells(subIdColumn, lastRow + 1, subIdColumn, subRow - 1);
                                                                addCell(sheet, subIdColumn, lastRow + 1, ++subIdVal, ce.getCellFormat(), 0);
                                                            }
                                                            lastRow = subRow - 1;
                                                            subTotal = 0;
                                                        }
                                                        value = sheet.getRow(subRow)[subFieldColumn].getContents();
                                                        if (value != null && !value.equals("")) {
                                                            subTotal += Double.parseDouble(value);
                                                        }
                                                        if (m == (v.size() - 1)) {
                                                            sheet.mergeCells(column, lastRow + 1, column, subRow);
                                                            addCell(sheet, column, lastRow + 1, subTotal, ce.getCellFormat(), subDigit);
                                                            if (subIdColumn != -1) {
                                                                sheet.mergeCells(subIdColumn, lastRow + 1, subIdColumn, subRow);
                                                                addCell(sheet, subIdColumn, lastRow + 1, ++subIdVal, ce.getCellFormat(), 0);
                                                            }
                                                        }
                                                        lastCellCotents = cellContents;
                                                        subRow++;
                                                    } else {
                                                        if (m == 0) {
                                                            sb.append(getValue(record.get(attStr), dateFormat));
                                                        } else {
                                                            Cell ce = sheet.getCell(column, row);
                                                            addCell(sheet, column, row + m, getValue(
                                                                    record.get(attStr), dateFormat), type, ce
                                                                    .getCellFormat());

                                                        }
                                                    }
                                                }
                                            } else {//判断+后有无数字
                                                expandCount = Integer.parseInt(attStr.substring(addIdx + 1));
                                                attStr = attStr.substring(0, addIdx);
                                                if (!expanded) {
                                                    expandRow(sheet, row, expandCount, v.size());
                                                    expanded = true;
                                                }
                                                int subIdVal = 0;
                                                for (int m = 0; m < expandCount; m++) {
                                                    if (m < v.size()) {
                                                        Map record = (Map) v.get(m);
                                                        if (subFieldColumn != -1 && attStr.equals("#subtotal")) {
                                                            subTotalCol = column;//记录小计的列,以防止重写模版行的时候覆盖小计第一行的数据
                                                            Cell ce = sheet.getCell(column, row);
                                                            cellContents = sheet.getRow(subRow)[subGroupColumn].getContents();
                                                            if (lastCellCotents == null)
                                                                lastCellCotents = cellContents;
                                                            if (cellContents != null && lastCellCotents != null && !lastCellCotents.equals(cellContents)) {
                                                                sheet.mergeCells(column, lastRow + 1, column, subRow - 1);//合计小计的单元格
                                                                addCell(sheet, column, lastRow + 1, subTotal, ce.getCellFormat(), subDigit);
                                                                if (subIdColumn != -1) {
                                                                    sheet.mergeCells(subIdColumn, lastRow + 1, subIdColumn, subRow - 1);
                                                                    addCell(sheet, subIdColumn, lastRow + 1, ++subIdVal, ce.getCellFormat(), 0);
                                                                }
                                                                lastRow = subRow - 1;
                                                                subTotal = 0;
                                                            }
                                                            value = sheet.getRow(subRow)[subFieldColumn].getContents();
                                                            if (value != null && !value.equals("")) {
                                                                subTotal += Double.parseDouble(value);
                                                            }
                                                            if (m == (expandCount - 1) || m == (v.size() - 1)) {
                                                                sheet.mergeCells(column, lastRow + 1, column, subRow);
                                                                addCell(sheet, column, lastRow + 1, subTotal, ce.getCellFormat(), subDigit);
                                                                if (subIdColumn != -1) {
                                                                    sheet.mergeCells(subIdColumn, lastRow + 1, subIdColumn, subRow);
                                                                    addCell(sheet, subIdColumn, lastRow + 1, ++subIdVal, ce.getCellFormat(), 0);
                                                                }
                                                            }
                                                            lastCellCotents = cellContents;
                                                            subRow++;
                                                        } else {
                                                            if (m == 0) {
                                                                sb.append(getValue(record.get(attStr), dateFormat));
                                                            } else {
                                                                Cell ce = sheet.getCell(column, row);
                                                                addCell(sheet, column, row + m, getValue(
                                                                        record.get(attStr), dateFormat), type, ce
                                                                        .getCellFormat());

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        sb.append('{').append(token).append('}');
                                    }
                                } else
                                    sb.append(c);
                            }
                            reader.close();
                            if (column != subTotalCol) {
                                addCell(sheet, label.getColumn(), label.getRow(), sb.toString(), type, label
                                        .getCellFormat());
                            }
                        }
                    }
                }
            }

            outputWorkbook.write();
            outputWorkbook.close();

            if (protect) {
                //protect excel
                POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(outputExcel));
                HSSFWorkbook wb = new HSSFWorkbook(fs);

                int sheets = wb.getNumberOfSheets();
                for (int i = 0; i < sheets; i++) {
                    HSSFSheet sheet = wb.getSheetAt(i);
                    sheet.protectSheet("qapmoc");
                }
                // Write the output to a file
                FileOutputStream fileOut = new FileOutputStream(outputExcel);
                wb.write(fileOut);
                fileOut.close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private static void addCell(WritableSheet sheet, int column, int row, String value, String type, CellFormat cf)
            throws RowsExceededException, WriteException {
        addCell(sheet, column, row, value, type, cf, false);
    }

    private static void addCell(WritableSheet sheet, int column, int row, String value, String type, CellFormat cf, boolean hasBorder)
            throws RowsExceededException, WriteException {
        WritableCell addCell;
        if (type.startsWith("number")) {
            if (value != null && !value.equals("")) {
                int scale = 2;
                try {
                    scale = Integer.parseInt(type.substring(6));
                } catch (Exception e) {
                    /** ignore it */
                }
                double dvalue = 0;
                try {
                    dvalue = new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
                } catch (Exception e) {
                    /** ignore it */
                }
                StringBuffer sb = new StringBuffer("0");
                if (scale > 0) {
                    sb.append(".");
                }
                for (int i = 0; i < scale; i++) {
                    sb.append("0");
                }
                NumberFormat nf = new NumberFormat(sb.toString());
                WritableCellFormat wcf = new WritableCellFormat(new WritableFont(cf.getFont()), nf);// add WritableFont by suntian to keep font size
                wcf.setAlignment(cf.getAlignment());
                wcf.setBackground(cf.getBackgroundColour(), cf.getPattern());
                //Border added by suntian
                if (cf.getBorder(Border.ALL) != BorderLineStyle.NONE || hasBorder) {
                    wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
                } else {
                    if (cf.getBorder(Border.TOP) != BorderLineStyle.NONE) {
                        wcf.setBorder(Border.TOP, BorderLineStyle.THIN);
                    }
                    if (cf.getBorder(Border.BOTTOM) != BorderLineStyle.NONE) {
                        wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
                    }
                    if (cf.getBorder(Border.LEFT) != BorderLineStyle.NONE) {
                        wcf.setBorder(Border.LEFT, BorderLineStyle.THIN);
                    }
                    if (cf.getBorder(Border.RIGHT) != BorderLineStyle.NONE) {
                        wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN);
                    }
                }
                wcf.setOrientation(cf.getOrientation());
                wcf.setShrinkToFit(cf.isShrinkToFit());
                wcf.setVerticalAlignment(cf.getVerticalAlignment());
                wcf.setWrap(cf.getWrap());
                addCell = new Number(column, row, dvalue, wcf);
            } else {
                addCell = new Label(column, row, "", cf);
            }
        } else {
            addCell = new Label(column, row, value, cf);
        }
        sheet.addCell(addCell);
    }

    private static void addCell(WritableSheet sheet, int column, int row, double subTotal, CellFormat cf, int subDigit)
            throws RowsExceededException, WriteException {
        addCell(sheet, column, row, new Double(subTotal).toString(), "number" + new Integer(subDigit).toString(), cf, true);
    }

    private static void formatFormulaCell(Sheet originSheet, WritableSheet genSheet) throws FormulaException,
            WriteException, WriteException {
        for (int row = 0; row < originSheet.getRows(); row++) {
            Cell[] rowCells = originSheet.getRow(row);
            for (int column = 0; column < rowCells.length; column++) {
                Cell cell = rowCells[column];
                if (cell.getType() == CellType.NUMBER_FORMULA || cell.getType() == CellType.STRING_FORMULA
                        || cell.getType() == CellType.BOOLEAN_FORMULA || cell.getType() == CellType.FORMULA_ERROR) {
                    FormulaCell fc = (FormulaCell) cell;
                    String form = fc.getFormula();
                    //add by suntian to add default value to sum formula if it is has an error
                    if (form.toUpperCase().indexOf("SUM") != -1 && form.toUpperCase().indexOf("ISERROR") == -1) {
                        form = "IF(ISERROR(" + form + "),0," + form + ")";
                    }
                    Formula formula = new Formula(cell.getColumn(), cell.getRow(), form, cell
                            .getCellFormat());
                    genSheet.addCell(formula);
                }
            }
        }
    }

    private static List getFormulaCellList(WritableSheet sheet) {
        List result = new ArrayList();
        for (int row = 0; row < sheet.getRows(); row++) {
            Cell[] rowCells = sheet.getRow(row);
            for (int column = 0; column < rowCells.length; column++) {
                Cell cell = rowCells[column];
                if (cell instanceof Formula) {
                    result.add(cell);
                }
            }
        }
        return result;
    }

    private static String getValue(Object valueObj, String dateFormat) {
        if (valueObj != null) {
            if (valueObj instanceof Date) {
                Date date = (Date) valueObj;
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                String textValue = sdf.format(date);
                //return DateUtil.toDate((String) valueObj, dateFormat).toString();
                return textValue;
            } else
                return valueObj.toString();
        } else {
            return "";
        }
    }

    private static int getColWidth(WritableSheet sheet, int column, int curRow) {
        Range[] ranges = sheet.getMergedCells();
        for (int i = 0; i < ranges.length; i++) {
            Range range = ranges[i];
            Cell leftCell = range.getTopLeft();
            Cell rightCell = range.getBottomRight();
            if (leftCell.getColumn() == column && leftCell.getRow() == curRow) {
                return rightCell.getColumn() - leftCell.getColumn() + 1;
            }
        }
        return 1;
    }

    private static void expandRow(WritableSheet sheet, int baseRow, int expandCount, int recordCount)
            throws RowsExceededException, WriteException, FormulaException, IOException {
        List addFormulaCellList = new ArrayList();
        Cell[] rowCells = sheet.getRow(baseRow);//模版行
        boolean flag = false;//设置模版行高度的标志
        for (int i = 1; i < expandCount; i++) {//循环复制行
            int row = baseRow + i;
            sheet.insertRow(row);//插入空白行
            int column = 0;
            for (int j = 0; j < rowCells.length; j++) {
                Cell ce = rowCells[j];
                if (!flag && ce.getCellFormat() != null && ce.getCellFormat().getWrap()) {//防止行高度根据模版而不是当前数据改变，设置为基本高度
                    sheet.setRowView(baseRow, 14);
                    flag = true;
                }
                int colspan = getColWidth(sheet, column, baseRow);//得到列的宽度,占几个单元格
                if (colspan > 1) {//如果占了两个或者两个以上的单元格
                    // merge cells
                    sheet.mergeCells(column, row, column + colspan - 1, row);
                    Cell copyCell = sheet.getCell(column, baseRow);
                    if (copyCell instanceof Formula) {
                        if (recordCount > i) {
                            Formula copyFormula = (Formula) copyCell;
                            Formula formula = new Formula(column, row, addFormulaRefRow(copyFormula.getContents(),
                                    baseRow, i + 1), copyCell.getCellFormat());
                            addFormulaCellList.add(formula);
                        }
                    }
                    Label label = new Label(column, row, "", copyCell.getCellFormat());
                    sheet.addCell(label);
                    // fill in blank cells of the spanned column
                    for (int k = 1; k < colspan; k++) {
                        Blank blank = new Blank(column + k, row, sheet.getCell(column + k, baseRow).getCellFormat());
                        sheet.addCell(blank);
                    }
                    // set row height
                    sheet.setRowView(row, sheet.getRowView(baseRow).getDimension() / 20);
                } else {
                    Cell copyCell = sheet.getCell(column, baseRow);
                    if (copyCell instanceof Formula) {
                        if (recordCount > i) {
                            Formula copyFormula = (Formula) copyCell;
                            Formula formula = new Formula(column, row, addFormulaRefRow(copyFormula.getContents(),
                                    baseRow, i + 1), copyCell.getCellFormat());
                            addFormulaCellList.add(formula);
                        }
                    }
                }
                // calculate current column
                column = column + colspan;
            }
        }

        List formulaCellList = getFormulaCellList(sheet);
        for (int l = 0; l < formulaCellList.size(); l++) {
            Formula formula = (Formula) formulaCellList.get(l);
            Reader reader = new StringReader(formula.getContents().toUpperCase());
            char c;
            int i;
            StringBuffer sb = new StringBuffer();
            boolean change = false;
            while ((i = reader.read()) != -1) {
                c = (char) i;
                if (c > 'A' || c < 'Z') {
                    StringBuffer buf = new StringBuffer();
                    buf.append(c);

                    int j = reader.read();
                    c = (char) j;
                    boolean hasDigit = false;
                    while (Character.isLetter(c) && j != -1) {
                        buf.append(c);
                        j = reader.read();
                        c = (char) j;
                    }
                    if (Character.isDigit(c) && j != -1) {
                        buf.append(c);
                        j = reader.read();
                        c = (char) j;
                        while (Character.isDigit(c) && j != -1) {
                            buf.append(c);
                            j = reader.read();
                            c = (char) j;
                        }
                        hasDigit = true;
                    }
                    if (hasDigit) {
                        int refRow = CellReferenceHelper.getRow(buf.toString());
                        if (refRow > baseRow) {
                            change = true;
                            CellReferenceHelper.getCellReference(CellReferenceHelper.getColumn(buf.toString()), refRow
                                    + expandCount - 1, sb);
                        } else if (refRow == baseRow) {
                            if (formula.getRow() == baseRow) {

                            } else {
                                change = true;
                                CellReferenceHelper.getCellReference(CellReferenceHelper.getColumn(buf.toString()),
                                        refRow, sb);
                                sb.append(":");
                                CellReferenceHelper.getCellReference(CellReferenceHelper.getColumn(buf.toString()),
                                        refRow + expandCount - 1, sb);
                            }
                        } else {
                            sb.append(buf.toString());
                        }
                    } else {
                        sb.append(buf);
                    }
                    if (j != -1) {
                        sb.append(c);
                    }
                } else
                    sb.append(c);
            }
            reader.close();
            if (change) {
                Formula newFormula = new Formula(formula.getColumn(), formula.getRow(), sb.toString(), formula
                        .getCellFormat());
                sheet.addCell(newFormula);
            }
        }

        for (int i = 0; i < addFormulaCellList.size(); i++) {
            sheet.addCell((Formula) addFormulaCellList.get(i));
        }
    }

    private static String addFormulaRefRow(String formula, int baseRow, int expandCount) throws IOException {
        Reader reader = new StringReader(formula.toUpperCase());
        char c;
        int i;
        StringBuffer sb = new StringBuffer();
        while ((i = reader.read()) != -1) {
            c = (char) i;
            if (c > 'A' || c < 'Z') {
                StringBuffer buf = new StringBuffer();
                buf.append(c);

                int j = reader.read();
                c = (char) j;
                boolean hasDigit = false;
                while (Character.isLetter(c) && j != -1) {
                    buf.append(c);
                    j = reader.read();
                    c = (char) j;
                }
                if (Character.isDigit(c) && j != -1) {
                    buf.append(c);
                    j = reader.read();
                    c = (char) j;
                    while (Character.isDigit(c) && j != -1) {
                        buf.append(c);
                        j = reader.read();
                        c = (char) j;
                    }
                    hasDigit = true;
                }
                if (hasDigit) {
                    int refRow = CellReferenceHelper.getRow(buf.toString());
                    if (refRow == baseRow) {
                        CellReferenceHelper.getCellReference(CellReferenceHelper.getColumn(buf.toString()), refRow
                                + expandCount - 1, sb);
                    } else {
                        sb.append(buf.toString());
                    }
                } else {
                    sb.append(buf);
                }
                if (j != -1) {
                    sb.append(c);
                }
            } else
                sb.append(c);
        }
        reader.close();
        return sb.toString();
    }
}