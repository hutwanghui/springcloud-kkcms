package com.kk.common.util;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public final class FileUtility {
    /**
     * Private constructor to prevent instantiation.
     */
    private FileUtility() {
    }

    /**
     * Compare the contents of two files to determine if they are equal or not.
     *
     * @param file1 the first file
     * @param file2 the second file
     * @return true if the content of the files are equal or they both don't exist, false otherwise
     */
    public static boolean contentEquals(final File file1, final File file2) throws IOException {
        final boolean file1Exists = file1.exists();
        if (file1Exists != file2.exists()) {
            return false;
        }

        if (!file1Exists) {
            // two not existing files are equal
            return true;
        }

        if (file1.isDirectory() || file2.isDirectory()) {
            // don't want to compare directory contents
            return false;
        }

        InputStream input1 = null;
        InputStream input2 = null;
        try {
            input1 = new FileInputStream(file1);
            input2 = new FileInputStream(file2);
            final InputStream bufferedInput1 = new BufferedInputStream(input1);
            final InputStream bufferedInput2 = new BufferedInputStream(input2);

            int ch = bufferedInput1.read();
            while (ch != -1) {
                if (ch != bufferedInput2.read()) {
                    return false;
                }
                ch = bufferedInput1.read();
            }
            if (-1 != bufferedInput2.read()) {
                return false;
            }
            return true;
        } finally {
            IOUtility.shutdownStream(input1);
            IOUtility.shutdownStream(input2);
        }
    }

    /**
     * Convert from a <code>URL</code> to a <code>File</code>.
     * @param url File URL.
     * @return The equivalent <code>File</code> object, or <code>null</code> if the URL's protocol
     * is not <code>file</code>
     */
    public static File toFile(final URL url) {
        if (url.getProtocol().equals("file") == false) {
            return null;
        } else {
            final String filename = url.getFile().replace('/', File.separatorChar);
            return new File(filename);
        }
    }

   
    /**
     * Remove extension from filename.
     * ie
     * <pre>
     * foo.txt    --> foo
     * a\b\c.jpg --> a\b\c
     * a\b\c     --> a\b\c
     * </pre>
     *
     * @param filename the filename
     * @return the filename minus extension
     * @deprecated Use removeExtension as removeExtention is mispelled
     */
    public static String removeExtention(final String filename) {
        return removeExtension(filename);
    }

    /**
     * Remove extension from filename.
     * ie
     * <pre>
     * foo.txt    --> foo
     * a\b\c.jpg --> a\b\c
     * a\b\c     --> a\b\c
     * </pre>
     *
     * @param filename the filename
     * @return the filename minus extension
     */
    public static String removeExtension(final String filename) {
        final int index = filename.lastIndexOf('.');

        if (-1 == index) {
            return filename;
        } else {
            return filename.substring(0, index);
        }
    }

    /**
     * Get extension from filename.
     * ie
     * <pre>
     * foo.txt    --> "txt"
     * a\b\c.jpg --> "jpg"
     * a\b\c     --> ""
     * </pre>
     *
     * @param filename the filename
     * @return the extension of filename or "" if none
     */
    public static String getExtension(final String filename) {
        final int index = filename.lastIndexOf('.');

        if (-1 == index) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

    /**
     * Remove path from filename. Equivalent to the unix command <code>basename</code>
     * ie.
     * <pre>
     * a/b/c.txt --> c.txt
     * a.txt     --> a.txt
     * </pre>
     *
     * @param filepath the filepath
     * @return the filename minus path
     */
    public static String removePath(final String filepath) {
        return removePath(filepath, File.separatorChar);
    }

    /**
     * Remove path from filename.
     * ie.
     * <pre>
     * a/b/c.txt --> c.txt
     * a.txt     --> a.txt
     * </pre>
     *
     * @param filepath the filepath
     * @return the filename minus path
     */
    public static String removePath(final String filepath, final char fileSeparatorChar) {
        final int index = filepath.lastIndexOf(fileSeparatorChar);

        if (-1 == index) {
            return filepath;
        } else {
            return filepath.substring(index + 1);
        }
    }

    /**
     * Get path from filename. Roughly equivalent to the unix command <code>dirname</code>.
     * ie.
     * <pre>
     * a/b/c.txt --> a/b
     * a.txt     --> ""
     * </pre>
     *
     * @param filepath the filepath
     * @return the filename minus path
     */
    public static String getPath(final String filepath) {
        return getPath(filepath, File.separatorChar);
    }

    /**
     * Get path from filename.
     * ie.
     * <pre>
     * a/b/c.txt --> a/b
     * a.txt     --> ""
     * </pre>
     *
     * @param filepath the filepath
     * @return the filename minus path
     */
    public static String getPath(final String filepath, final char fileSeparatorChar) {
        final int index = filepath.lastIndexOf(fileSeparatorChar);
        if (-1 == index) {
            return "";
        } else {
            return filepath.substring(0, index);
        }
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
    public static void copyFileToDirectory(final String source, final String destinationDirectory) throws IOException {
        copyFileToDirectory(new File(source), new File(destinationDirectory));
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
    public static void copyFileToDirectory(final File source, final File destinationDirectory) throws IOException {
        if (destinationDirectory.exists() && !destinationDirectory.isDirectory()) {
            throw new IllegalArgumentException("Destination is not a directory");
        }

        copyFile(source, new File(destinationDirectory, source.getName()));
    }

    /**
     * Copy file from source to destination. The directories up to <code>destination</code> will be
     * created if they don't already exist. <code>destination</code> will be overwritten if it
     * already exists.
     *
     * @param source An existing non-directory <code>File</code> to copy bytes from.
     * @param destination A non-directory <code>File</code> to write bytes to (possibly
     * overwriting).
     *
     * @throws IOException if <code>source</code> does not exist, <code>destination</code> cannot be
     * written to, or an IO error occurs during copying.
     *
     * @throws FileNotFoundException if <code>destination</code> is a directory
     * (use {@link #copyFileToDirectory}).
     */
    public static void copyFile(final File source, final File destination) throws IOException {
        //check source exists
        if (!source.exists()) {
            final String message = "File " + source + " does not exist";
            throw new IOException(message);
        }

        //does destinations directory exist ?
        if (destination.getParentFile() != null && !destination.getParentFile().exists()) {
            destination.getParentFile().mkdirs();
        }

        //make sure we can write to destination
        if (destination.exists() && !destination.canWrite()) {
            final String message = "Unable to open file " + destination + " for writing.";
            throw new IOException(message);
        }

        final FileInputStream input = new FileInputStream(source);
        final FileOutputStream output = new FileOutputStream(destination);
        IOUtility.copy(input, output);
        IOUtility.shutdownStream(input);
        IOUtility.shutdownStream(output);

        if (source.length() != destination.length()) {
            final String message = "Failed to copy full contents from " + source + " to " + destination;
            throw new IOException(message);
        }
    }
    
    public static void copyInputStreamFile(final InputStream source, final File destination) throws IOException {
    	
    	final DataInputStream dis_2=new DataInputStream(source);
    	
        //final FileInputStream input = (FileInputStream)dis_2;
        final FileOutputStream output = new FileOutputStream(destination);
        IOUtility.copy(dis_2, output);
        IOUtility.shutdownStream(dis_2);
        IOUtility.shutdownStream(output);
    }
    
    
    /*
    public static void copyFilenametoFile(final String sourcename, final File destination) throws IOException {
        //check source exists\
    	File source = new File(AppContext.getTempFilesDir() , sourcename.substring(10));
  
        if (!source.exists()) {
            final String message = "File " + source + " does not exist";
            throw new IOException(message);
        }

        //does destinations directory exist ?
        if (destination.getParentFile() != null && !destination.getParentFile().exists()) {
            destination.getParentFile().mkdirs();
        }

        //make sure we can write to destination
        if (destination.exists() && !destination.canWrite()) {
            final String message = "Unable to open file " + destination + " for writing.";
            throw new IOException(message);
        }

        final FileInputStream input = new FileInputStream(source);
        final FileOutputStream output = new FileOutputStream(destination);
        IOUtility.copy(input, output);
        IOUtility.shutdownStream(input);
        IOUtility.shutdownStream(output);

        if (source.length() != destination.length()) {
            final String message = "Failed to copy full contents from " + source + " to " + destination;
            throw new IOException(message);
        }
    }
    */
    
   
    /**
     * Copies bytes from the URL <code>source</code> to a file <code>destination</code>.
     * The directories up to <code>destination</code> will be created if they don't already exist.
     * <code>destination</code> will be overwritten if it already exists.
     *
     * @param source A <code>URL</code> to copy bytes from.
     * @param destination A non-directory <code>File</code> to write bytes to (possibly
     * overwriting).
     *
     * @throws IOException if
     * <ul>
     *  <li><code>source</code> URL cannot be opened</li>
     *  <li><code>destination</code> cannot be written to</li>
     *  <li>an IO error occurs during copying</li>
     * </ul>
     */
    public static void copyURLToFile(final URL source, final File destination) throws IOException {
        //does destination directory exist ?
        if (destination.getParentFile() != null && !destination.getParentFile().exists()) {
            destination.mkdirs();
        }

        //make sure we can write to destination
        if (destination.exists() && !destination.canWrite()) {
            final String message = "Unable to open file " + destination + " for writing.";
            throw new IOException(message);
        }

        final InputStream input = source.openStream();
        final FileOutputStream output = new FileOutputStream(destination);
        IOUtility.copy(input, output);
        IOUtility.shutdownStream(input);
        IOUtility.shutdownStream(output);
    }

    /**
     * Normalize a path.
     * Eliminates "/../" and "/./" in a string. Returns <code>null</code> if the ..'s went past the
     * root.
     * Eg:
     * <pre>
     * /foo//               -->     /foo/
     * /foo/./              -->     /foo/
     * /foo/../bar          -->     /bar
     * /foo/../bar/         -->     /bar/
     * /foo/../bar/../baz   -->     /baz
     * //foo//./bar         -->     /foo/bar
     * /../                 -->     null
     * </pre>
     *
     * @param path the path to normalize
     * @return the normalized String, or <code>null</code> if too many ..'s.
     */
    public static String normalize(final String path) {
        String normalized = path;
        // Resolve occurrences of "//" in the normalized path
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) + normalized.substring(index + 1);
        }

        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) + normalized.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0)
                break;
            if (index == 0)
                return null; // Trying to go outside our context
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
        }

        // Return the normalized path that we have completed
        return normalized;
    }

    /**
     * Will concatenate 2 paths, dealing with <code>..</code>
     * <p>Eg.,<br />
     * <code>/a/b/c</code> + <code>d</code> = <code>/a/b/d</code><br />
     * <code>/a/b/c</code> + <code>../d</code> = <code>/a/d</code><br />
     * </p>
     *
     * Thieved from Tomcat sources...
     *
     * @return The concatenated paths, or null if error occurs
     */
    public static String catPath(final String lookupPath, final String path) {
        // Cut off the last slash and everything beyond
        int index = lookupPath.lastIndexOf("/");
        String lookup = lookupPath.substring(0, index);
        String pth = path;

        // Deal with .. by chopping dirs off the lookup path
        while (pth.startsWith("../")) {
            if (lookup.length() > 0) {
                index = lookup.lastIndexOf("/");
                lookup = lookup.substring(0, index);
            } else {
                // More ..'s than dirs, return null
                return null;
            }

            index = pth.indexOf("../") + 3;
            pth = pth.substring(index);
        }

        return new StringBuffer(lookup).append("/").append(pth).toString();
    }

    /**
     * Resolve a file <code>filename</code> to it's canonical form. If <code>filename</code> is
     * relative (doesn't start with <code>/</code>), it will be resolved relative to
     * <code>baseFile</code>, otherwise it is treated as a normal root-relative path.
     *
     * @param baseFile Where to resolve <code>filename</code> from, if <code>filename</code> is
     * relative.
     * @param filename Absolute or relative file path to resolve.
     * @return The canonical <code>File</code> of <code>filename</code>.
     */
    public static File resolveFile(final File baseFile, String filename) {
        String filenm = null;
        if ('/' != File.separatorChar) {
            filenm = filename.replace('/', File.separatorChar);
        }

        if ('\\' != File.separatorChar) {
            filenm = filename.replace('\\', File.separatorChar);
        }

        // deal with absolute files
        if (filenm.startsWith(File.separator)) {
            File file = new File(filenm);

            try {
                file = file.getCanonicalFile();
            } catch (final IOException ioe) {
            }

            return file;
        }
        // FIXME: I'm almost certain this // removal is unnecessary, as getAbsoluteFile() strips
        // them. However, I'm not sure about this UNC stuff. (JT)
        final char[] chars = filename.toCharArray();
        final StringBuffer sb = new StringBuffer();

        //remove duplicate file separators in succession - except
        //on win32 at start of filename as UNC filenames can
        //be \\AComputer\AShare\myfile.txt
        int start = 0;
        if ('\\' == File.separatorChar) {
            sb.append(filenm.charAt(0));
            start++;
        }

        for (int i = start; i < chars.length; i++) {
            final boolean doubleSeparator = File.separatorChar == chars[i] && File.separatorChar == chars[i - 1];

            if (!doubleSeparator) {
                sb.append(chars[i]);
            }
        }

        filenm = sb.toString();

        //must be relative
        File file = (new File(baseFile, filenm)).getAbsoluteFile();

        try {
            file = file.getCanonicalFile();
        } catch (final IOException ioe) {
        }

        return file;
    }

    /**
     * Delete a file. If file is directory delete it and all sub-directories.
     */
    public static void forceDelete(final String file) throws IOException {
        forceDelete(new File(file));
    }

    /**
     * Delete a file. If file is directory delete it and all sub-directories.
     */
    public static void forceDelete(final File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            if (false == file.delete()) {
                final String message = "File " + file + " unable to be deleted.";
                throw new IOException(message);
            }
        }
    }

    /**
     * Recursively delete a directory.
     */
    public static void deleteDirectory(final String directory) throws IOException {
        deleteDirectory(new File(directory));
    }

    /**
     * Recursively delete a directory.
     */
    public static void deleteDirectory(final File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        cleanDirectory(directory);
        if (false == directory.delete()) {
            throw new IOException("Directory " + directory + " unable to be deleted.");
        }
    }

    /**
     * Clean a directory without deleting it.
     */
    public static void cleanDirectory(final String directory) throws IOException {
        cleanDirectory(new File(directory));
    }

    /**
     * Clean a directory without deleting it.
     */
    public static void cleanDirectory(final File directory) throws IOException {
        if (!directory.exists()) {
            final String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            final String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        final File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++) {
            final File file = files[i];

            FileUtility.forceDelete(file);
        }
    }

    /**
     * Recursively count size of a directory.
     *
     * @return size of directory in bytes.
     */
    public static long sizeOfDirectory(final String directory) {
        return sizeOfDirectory(new File(directory));
    }

    /**
     * Recursively count size of a directory.
     *
     * @return size of directory in bytes.
     */
    public static long sizeOfDirectory(final File directory) {
        if (!directory.exists()) {
            final String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            final String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        long size = 0;

        final File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            final File file = files[i];

            if (file.isDirectory()) {
                size += sizeOfDirectory(file);
            } else {
                size += file.length();
            }
        }

        return size;
    }

    /** 静态随机生成器 */
    private static Random random = new Random();

    /**
     * Get an unique file
     * @param ext file name extension
     * @return an unique file that not exists 
     * @throws IOException 
     */
    
    public static File getUniqueFile(String realPath,String formatName) throws IOException {

    	Date date = new Date();//获取当前时间    
        SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMddHHmmss");    
        //SimpleDateFormat sdfFolder = new SimpleDateFormat("yyMM");    
        String newfileName = sdfFileName.format(date);//文件名称    
        
        File result;
        do {
            long r = random.nextInt(9999);
            result = new File(realPath, "" + newfileName + "_" + r + formatName);
        } while (!result.createNewFile());
        return result;
    }
    /*
    public static File getUniqueFile(String ext) throws IOException {
        long sc = System.currentTimeMillis();
        File result;
        do {
            long r = random.nextInt(9999);
            result = new File(AppContext.getTempDir(), "" + sc + r + "." + ext);
        } while (!result.createNewFile());
        return result;
    }
    
    public static File getUniqueUpFile(String ext) throws IOException {
        long sc = System.currentTimeMillis();
        File result;
        do {
            long r = random.nextInt(9999);
            result = new File(AppContext.getFilesDir(), "" + sc + r + "." + ext);
        } while (!result.createNewFile());
        return result;
    }
    
    public static File getUniqueUpTempFile(String ext) throws IOException {
        long sc = System.currentTimeMillis();
        File result;
        do {
            long r = random.nextInt(9999);
            result = new File(AppContext.getTempFilesDir(), "" + sc + r + "." + ext);
        } while (!result.createNewFile());
        return result;
    }

    public static File getUniqueDir() throws IOException {
        long sc = System.currentTimeMillis();
        File result;
        do {
            long r = random.nextInt(9999);
            result = new File(AppContext.getTempDir(), "" + sc + r);
        } while (!result.mkdirs());
        return result;
    }
    */

}