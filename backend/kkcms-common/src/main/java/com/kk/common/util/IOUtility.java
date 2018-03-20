package com.kk.common.util;

import java.io.*;
import java.util.zip.ZipInputStream;

public final class IOUtility {
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * Private constructor to prevent instantiation.
     */
    private IOUtility() {
    }

    /**
     * Unconditionally close an <code>Reader</code>.
     * Equivalent to {@link Reader#close()}, except any exceptions will be ignored.
     *
     * @param output A (possibly null) Reader
     */
    public static void shutdownReader(final Reader input) {
        if (null == input) {
            return;
        }

        try {
            input.close();
        } catch (final IOException ioe) {
        }
    }

    /**
     * Unconditionally close an <code>Writer</code>.
     * Equivalent to {@link Writer#close()}, except any exceptions will be ignored.
     *
     * @param output A (possibly null) Writer
     */
    public static void shutdownWriter(final Writer output) {
        if (null == output) {
            return;
        }

        try {
            output.close();
        } catch (final IOException ioe) {
        }
    }

    /**
     * Unconditionally close an <code>OutputStream</code>.
     * Equivalent to {@link OutputStream#close()}, except any exceptions will be ignored.
     * @param output A (possibly null) OutputStream
     */
    public static void shutdownStream(final OutputStream output) {
        if (null == output) {
            return;
        }

        try {
            output.close();
        } catch (final IOException ioe) {
        }
    }

    /**
     * Unconditionally close an <code>InputStream</code>.
     * Equivalent to {@link InputStream#close()}, except any exceptions will be ignored.
     * @param input A (possibly null) InputStream
     */
    public static void shutdownStream(final InputStream input) {
        if (null == input) {
            return;
        }

        try {
            input.close();
        } catch (final IOException ioe) {
        }
    }

    ///////////////////////////////////////////////////////////////
    // Core copy methods
    ///////////////////////////////////////////////////////////////

    /**
     * Copy bytes from an <code>InputStream</code> to an <code>OutputStream</code>.
     */
    public static void copy(final InputStream input, final OutputStream output) throws IOException {
        copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Copy bytes from an <code>InputStream</code> to an <code>OutputStream</code>.
     * @param bufferSize Size of internal buffer to use.
     */
    public static void copy(final InputStream input, final OutputStream output, final int bufferSize)
            throws IOException {
        final byte[] buffer = new byte[bufferSize];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }

    /**
     * Copy chars from a <code>Reader</code> to a <code>Writer</code>.
     */
    public static void copy(final Reader input, final Writer output) throws IOException {
        copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Copy chars from a <code>Reader</code> to a <code>Writer</code>.
     * @param bufferSize Size of internal buffer to use.
     */
    public static void copy(final Reader input, final Writer output, final int bufferSize) throws IOException {
        final char[] buffer = new char[bufferSize];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }

    ///////////////////////////////////////////////////////////////
    // Derived copy methods
    // InputStream -> *
    ///////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////
    // InputStream -> Writer

    /**
     * Copy and convert bytes from an <code>InputStream</code> to chars on a
     * <code>Writer</code>.
     * The platform's default encoding is used for the byte-to-char conversion.
     */
    public static void copy(final InputStream input, final Writer output) throws IOException {
        copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Copy and convert bytes from an <code>InputStream</code> to chars on a
     * <code>Writer</code>.
     * The platform's default encoding is used for the byte-to-char conversion.
     * @param bufferSize Size of internal buffer to use.
     */
    public static void copy(final InputStream input, final Writer output, final int bufferSize) throws IOException {
        final InputStreamReader in = new InputStreamReader(input);
        copy(in, output, bufferSize);
    }

    /**
     * Copy and convert bytes from an <code>InputStream</code> to chars on a
     * <code>Writer</code>, using the specified encoding.
     * @param encoding The name of a supported character encoding. See the
     * <a href="http://www.iana.org/assignments/character-sets">IANA
     * Charset Registry</a> for a list of valid encoding types.
     */
    public static void copy(final InputStream input, final Writer output, final String encoding) throws IOException {
        final InputStreamReader in = new InputStreamReader(input, encoding);
        copy(in, output);
    }

    /**
     * Copy and convert bytes from an <code>InputStream</code> to chars on a
     * <code>Writer</code>, using the specified encoding.
     * @param encoding The name of a supported character encoding. See the
     *        <a href="http://www.iana.org/assignments/character-sets">IANA
     *        Charset Registry</a> for a list of valid encoding types.
     * @param bufferSize Size of internal buffer to use.
     */
    public static void copy(final InputStream input, final Writer output, final String encoding, final int bufferSize)
            throws IOException {
        final InputStreamReader in = new InputStreamReader(input, encoding);
        copy(in, output, bufferSize);
    }

    ///////////////////////////////////////////////////////////////
    // InputStream -> String

    /**
     * Get the contents of an <code>InputStream</code> as a String.
     * The platform's default encoding is used for the byte-to-char conversion.
     */
    public static String toString(final InputStream input) throws IOException {
        return toString(input, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Get the contents of an <code>InputStream</code> as a String.
     * The platform's default encoding is used for the byte-to-char conversion.
     * @param bufferSize Size of internal buffer to use.
     */
    public static String toString(final InputStream input, final int bufferSize) throws IOException {
        final StringWriter sw = new StringWriter();
        copy(input, sw, bufferSize);
        return sw.toString();
    }

    /**
     * Get the contents of an <code>InputStream</code> as a String.
     * @param encoding The name of a supported character encoding. See the
     *    <a href="http://www.iana.org/assignments/character-sets">IANA
     *    Charset Registry</a> for a list of valid encoding types.
     */
    public static String toString(final InputStream input, final String encoding) throws IOException {
        return toString(input, encoding, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Get the contents of an <code>InputStream</code> as a String.
     * @param encoding The name of a supported character encoding. See the
     *   <a href="http://www.iana.org/assignments/character-sets">IANA
     *   Charset Registry</a> for a list of valid encoding types.
     * @param bufferSize Size of internal buffer to use.
     */
    public static String toString(final InputStream input, final String encoding, final int bufferSize)
            throws IOException {
        final StringWriter sw = new StringWriter();
        copy(input, sw, encoding, bufferSize);
        return sw.toString();
    }

    ///////////////////////////////////////////////////////////////
    // InputStream -> byte[]

    /**
     * Get the contents of an <code>InputStream</code> as a <code>byte[]</code>.
     */
    public static byte[] toByteArray(final InputStream input) throws IOException {
        return toByteArray(input, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Get the contents of an <code>InputStream</code> as a <code>byte[]</code>.
     * @param bufferSize Size of internal buffer to use.
     */
    public static byte[] toByteArray(final InputStream input, final int bufferSize) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output, bufferSize);
        return output.toByteArray();
    }

    ///////////////////////////////////////////////////////////////
    // Derived copy methods
    // Reader -> *
    ///////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////
    // Reader -> OutputStream
    /**
     * Serialize chars from a <code>Reader</code> to bytes on an <code>OutputStream</code>, and
     * flush the <code>OutputStream</code>.
     */
    public static void copy(final Reader input, final OutputStream output) throws IOException {
        copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Serialize chars from a <code>Reader</code> to bytes on an <code>OutputStream</code>, and
     * flush the <code>OutputStream</code>.
     * @param bufferSize Size of internal buffer to use.
     */
    public static void copy(final Reader input, final OutputStream output, final int bufferSize) throws IOException {
        final OutputStreamWriter out = new OutputStreamWriter(output);
        copy(input, out, bufferSize);
        // NOTE: Unless anyone is planning on rewriting OutputStreamWriter, we have to flush
        // here.
        out.flush();
    }

    ///////////////////////////////////////////////////////////////
    // Reader -> String
    /**
     * Get the contents of a <code>Reader</code> as a String.
     */
    public static String toString(final Reader input) throws IOException {
        return toString(input, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Get the contents of a <code>Reader</code> as a String.
     * @param bufferSize Size of internal buffer to use.
     */
    public static String toString(final Reader input, final int bufferSize) throws IOException {
        final StringWriter sw = new StringWriter();
        copy(input, sw, bufferSize);
        return sw.toString();
    }

    ///////////////////////////////////////////////////////////////
    // Reader -> byte[]
    /**
     * Get the contents of a <code>Reader</code> as a <code>byte[]</code>.
     */
    public static byte[] toByteArray(final Reader input) throws IOException {
        return toByteArray(input, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Get the contents of a <code>Reader</code> as a <code>byte[]</code>.
     * @param bufferSize Size of internal buffer to use.
     */
    public static byte[] toByteArray(final Reader input, final int bufferSize) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output, bufferSize);
        return output.toByteArray();
    }

    ///////////////////////////////////////////////////////////////
    // Derived copy methods
    // String -> *
    ///////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////
    // String -> OutputStream

    /**
     * Serialize chars from a <code>String</code> to bytes on an <code>OutputStream</code>, and
     * flush the <code>OutputStream</code>.
     */
    public static void copy(final String input, final OutputStream output) throws IOException {
        copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Serialize chars from a <code>String</code> to bytes on an <code>OutputStream</code>, and
     * flush the <code>OutputStream</code>.
     * @param bufferSize Size of internal buffer to use.
     */
    public static void copy(final String input, final OutputStream output, final int bufferSize) throws IOException {
        final StringReader in = new StringReader(input);
        final OutputStreamWriter out = new OutputStreamWriter(output);
        copy(in, out, bufferSize);
        // NOTE: Unless anyone is planning on rewriting OutputStreamWriter, we have to flush
        // here.
        out.flush();
    }

    ///////////////////////////////////////////////////////////////
    // String -> Writer

    /**
     * Copy chars from a <code>String</code> to a <code>Writer</code>.
     */
    public static void copy(final String input, final Writer output) throws IOException {
        output.write(input);
    }

    /**
     * Copy bytes from an <code>InputStream</code> to an
     * <code>OutputStream</code>, with buffering.
     * This is equivalent to passing a
     * {@link BufferedInputStream} and
     * {@link BufferedOutputStream} to {@link #copy(InputStream, OutputStream)},
     * and flushing the output stream afterwards. The streams are not closed
     * after the copy.
     * @deprecated Buffering streams is actively harmful! See the class description as to why. Use
     * {@link #copy(InputStream, OutputStream)} instead.
     */
    public static void bufferedCopy(final InputStream input, final OutputStream output) throws IOException {
        final BufferedInputStream in = new BufferedInputStream(input);
        final BufferedOutputStream out = new BufferedOutputStream(output);
        copy(in, out);
        out.flush();
    }

    ///////////////////////////////////////////////////////////////
    // String -> byte[]
    /**
     * Get the contents of a <code>String</code> as a <code>byte[]</code>.
     */
    public static byte[] toByteArray(final String input) throws IOException {
        return toByteArray(input, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Get the contents of a <code>String</code> as a <code>byte[]</code>.
     * @param bufferSize Size of internal buffer to use.
     */
    public static byte[] toByteArray(final String input, final int bufferSize) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output, bufferSize);
        return output.toByteArray();
    }

    ///////////////////////////////////////////////////////////////
    // Derived copy methods
    // byte[] -> *
    ///////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////
    // byte[] -> Writer

    /**
     * Copy and convert bytes from a <code>byte[]</code> to chars on a
     * <code>Writer</code>.
     * The platform's default encoding is used for the byte-to-char conversion.
     */
    public static void copy(final byte[] input, final Writer output) throws IOException {
        copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Copy and convert bytes from a <code>byte[]</code> to chars on a
     * <code>Writer</code>.
     * The platform's default encoding is used for the byte-to-char conversion.
     * @param bufferSize Size of internal buffer to use.
     */
    public static void copy(final byte[] input, final Writer output, final int bufferSize) throws IOException {
        final ByteArrayInputStream in = new ByteArrayInputStream(input);
        copy(in, output, bufferSize);
    }

    /**
     * Copy and convert bytes from a <code>byte[]</code> to chars on a
     * <code>Writer</code>, using the specified encoding.
     * @param encoding The name of a supported character encoding. See the
     * <a href="http://www.iana.org/assignments/character-sets">IANA
     * Charset Registry</a> for a list of valid encoding types.
     */
    public static void copy(final byte[] input, final Writer output, final String encoding) throws IOException {
        final ByteArrayInputStream in = new ByteArrayInputStream(input);
        copy(in, output, encoding);
    }

    /**
     * Copy and convert bytes from a <code>byte[]</code> to chars on a
     * <code>Writer</code>, using the specified encoding.
     * @param encoding The name of a supported character encoding. See the
     *        <a href="http://www.iana.org/assignments/character-sets">IANA
     *        Charset Registry</a> for a list of valid encoding types.
     * @param bufferSize Size of internal buffer to use.
     */
    public static void copy(final byte[] input, final Writer output, final String encoding, final int bufferSize)
            throws IOException {
        final ByteArrayInputStream in = new ByteArrayInputStream(input);
        copy(in, output, encoding, bufferSize);
    }

    ///////////////////////////////////////////////////////////////
    // byte[] -> String

    /**
     * Get the contents of a <code>byte[]</code> as a String.
     * The platform's default encoding is used for the byte-to-char conversion.
     */
    public static String toString(final byte[] input) throws IOException {
        return toString(input, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Get the contents of a <code>byte[]</code> as a String.
     * The platform's default encoding is used for the byte-to-char conversion.
     * @param bufferSize Size of internal buffer to use.
     */
    public static String toString(final byte[] input, final int bufferSize) throws IOException {
        final StringWriter sw = new StringWriter();
        copy(input, sw, bufferSize);
        return sw.toString();
    }

    /**
     * Get the contents of a <code>byte[]</code> as a String.
     * @param encoding The name of a supported character encoding. See the
     *    <a href="http://www.iana.org/assignments/character-sets">IANA
     *    Charset Registry</a> for a list of valid encoding types.
     */
    public static String toString(final byte[] input, final String encoding) throws IOException {
        return toString(input, encoding, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Get the contents of a <code>byte[]</code> as a String.
     * @param encoding The name of a supported character encoding. See the
     *   <a href="http://www.iana.org/assignments/character-sets">IANA
     *   Charset Registry</a> for a list of valid encoding types.
     * @param bufferSize Size of internal buffer to use.
     */
    public static String toString(final byte[] input, final String encoding, final int bufferSize) throws IOException {
        final StringWriter sw = new StringWriter();
        copy(input, sw, encoding, bufferSize);
        return sw.toString();
    }

    ///////////////////////////////////////////////////////////////
    // byte[] -> OutputStream

    /**
     * Copy bytes from a <code>byte[]</code> to an <code>OutputStream</code>.
     */
    public static void copy(final byte[] input, final OutputStream output) throws IOException {
        copy(input, output, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Copy bytes from a <code>byte[]</code> to an <code>OutputStream</code>.
     * @param bufferSize Size of internal buffer to use.
     */
    public static void copy(final byte[] input, final OutputStream output, final int bufferSize) throws IOException {
        output.write(input);
    }

    /**
     * Copy bytes from a <code>File</code> to an <code>File</code>.
     * @param bufferSize Size of internal buffer to use.
     */
    public static void copy(final File input, final File output) throws IOException {
        FileInputStream fis = new FileInputStream(input);
        // Add by Huchm 2004-06-03
        if (output.getParentFile() != null && !output.getParentFile().exists()) {
            output.getParentFile().mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(output);
        copy(fis, fos);
        fis.close();
        fos.flush();
        fos.close();
    }

    /**
     * 将某个目录(srcDir)下的文件、文件夹复制到指定目录(destineDir)中
     *
     * @param srcDir 要复制的目录
     * @param destineDir 要粘帖的目录
     * @throws IOException
     */
    public static void xcopy(File srcDir, File destineDir) throws IOException {
        try {
            if (!srcDir.exists() || srcDir.getCanonicalPath().equals(destineDir.getCanonicalPath()))
                return;
        } catch (IOException ioexception) {
        }
        if (!destineDir.exists()) {
            destineDir.mkdirs();
        }
        File chs[] = srcDir.listFiles();
        for (int i = 0; i < chs.length; i++) {
            if (chs[i].isFile()) {
                File destineFile = new File(destineDir, chs[i].getName());
                copy(chs[i], destineFile);
            } else {
                File destine = new File(destineDir, chs[i].getName());
                destine.mkdirs();
                xcopy(chs[i], destine);
            }
        }
    }

    /**
     * 把字节数组输出到文件中
     * @param bytes 字节数组
     * @param outputPath 指定的输出文件，自动建目录
     */
    public static void copy(final byte[] bytes, String outputPath) throws IOException {
        File output = new File(outputPath);
        if (output.getParentFile() != null && !output.getParentFile().exists()) {
            output.getParentFile().mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(output);
        copy(bytes, fos);
        fos.flush();
        fos.close();
    }

    /**
     * 把文件内容复制为目标文件，同时删除原文件 
     * @param src
     * @param destine
     * @throws IOException
     */
    public static void move(File src, File destine) throws IOException {
        try {
            if (!src.exists() || src.getCanonicalPath().equals(destine.getCanonicalPath()))
                return;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        copy(src, destine);
        src.delete();
    }

    /**
     * 把文件剪切到目标路径下
     * @param src
     * @param destDir
     * @throws IOException
     */
    public static void moveToDir(File src, File destDir) throws IOException {
        try {
            if (!src.exists() || src.getParentFile().getCanonicalPath().equals(destDir.getCanonicalPath()))
                return;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        copy(src, new File(destDir, src.getName()));
        src.delete();
    }

    /**
     * Copy file from source to destination. If <code>destinationDirectory</code> does not exist, it
     * (and any parent directories) will be created. If a file <code>source</code> in
     * <code>destinationDirectory</code> exists, it will be overwritten.
     *
     * @param source An existing <code>File</code> to copy.
     * @param destination A directory to copy <code>source</code> into.
     *
     * @throws FileNotFoundException if <code>source</code> isn't a normal file.
     * @throws IllegalArgumentException if <code>destinationDirectory</code> isn't a directory.
     * @throws IOException if <code>source</code> does not exist, the file in
     * <code>destinationDirectory</code> cannot be written to, or an IO error occurs during copying.
     */
    public static void copyFileToDir(final File source, final File destinationDirectory) throws IOException {
        if (destinationDirectory.exists() && !destinationDirectory.isDirectory()) {
            throw new IllegalArgumentException("Destination is not a directory");
        }

        copy(source, new File(destinationDirectory, source.getName()));
    }

    public static void unzip(String zipFileName, File outputDirectory) throws IOException {
        if (!outputDirectory.exists() || !outputDirectory.isDirectory())
            outputDirectory.mkdirs();
        ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
        java.util.zip.ZipEntry z;
        while ((z = in.getNextEntry()) != null) {

            if (z.isDirectory()) {
                String name = z.getName();
                name = name.substring(0, name.length() - 1);
                File f = new File(outputDirectory + File.separator + name);
                f.mkdir();
            } else {
                File f = new File(outputDirectory + File.separator + z.getName());
                f.createNewFile();
                FileOutputStream out = new FileOutputStream(f);
                int b;
                while ((b = in.read()) != -1)
                    out.write(b);
                out.flush();
                out.close();
            }
        }
        in.close();
    }

}