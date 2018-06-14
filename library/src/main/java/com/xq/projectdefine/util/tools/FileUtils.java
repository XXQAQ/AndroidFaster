package com.xq.projectdefine.util.tools;

import android.annotation.SuppressLint;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xq on 2017/8/22.
 */

public  class FileUtils {


    private FileUtils() {

        throw new UnsupportedOperationException("u can't instantiate me...");

    }


    private static final String LINE_SEP = System.getProperty("line.separator");



    /**

     * 根据文件路径获取文件

     *

     * @param filePath 文件路径

     * @return 文件

     */

    public static File getFileByPath(final String filePath) {

        return isSpace(filePath) ? null : new File(filePath);

    }



    /**

     * 判断文件是否存在

     *

     * @param filePath 文件路径

     * @return {@code true}: 存在<br>{@code false}: 不存在

     */

    public static boolean isFileExists(final String filePath) {

        return isFileExists(getFileByPath(filePath));

    }



    /**

     * 判断文件是否存在

     *

     * @param file 文件

     * @return {@code true}: 存在<br>{@code false}: 不存在

     */

    public static boolean isFileExists(final File file) {

        return file != null && file.exists();

    }



    /**

     * 重命名文件

     *

     * @param filePath 文件路径

     * @param newName  新名称

     * @return {@code true}: 重命名成功<br>{@code false}: 重命名失败

     */

    public static boolean rename(final String filePath, final String newName) {

        return rename(getFileByPath(filePath), newName);

    }



    /**

     * 重命名文件

     *

     * @param file    文件

     * @param newName 新名称

     * @return {@code true}: 重命名成功<br>{@code false}: 重命名失败

     */

    public static boolean rename(final File file, final String newName) {

        // 文件为空返回false

        if (file == null) return false;

        // 文件不存在返回false

        if (!file.exists()) return false;

        // 新的文件名为空返回false

        if (isSpace(newName)) return false;

        // 如果文件名没有改变返回true

        if (newName.equals(file.getName())) return true;

        File newFile = new File(file.getParent() + File.separator + newName);

        // 如果重命名的文件已存在返回false

        return !newFile.exists()

                && file.renameTo(newFile);

    }



    /**

     * 判断是否是目录

     *

     * @param dirPath 目录路径

     * @return {@code true}: 是<br>{@code false}: 否

     */

    public static boolean isDir(final String dirPath) {

        return isDir(getFileByPath(dirPath));

    }



    /**

     * 判断是否是目录

     *

     * @param file 文件

     * @return {@code true}: 是<br>{@code false}: 否

     */

    public static boolean isDir(final File file) {

        return isFileExists(file) && file.isDirectory();

    }



    /**

     * 判断是否是文件

     *

     * @param filePath 文件路径

     * @return {@code true}: 是<br>{@code false}: 否

     */

    public static boolean isFile(final String filePath) {

        return isFile(getFileByPath(filePath));

    }



    /**

     * 判断是否是文件

     *

     * @param file 文件

     * @return {@code true}: 是<br>{@code false}: 否

     */

    public static boolean isFile(final File file) {

        return isFileExists(file) && file.isFile();

    }



    /**

     * 判断目录是否存在，不存在则判断是否创建成功

     *

     * @param dirPath 目录路径

     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败

     */

    public static boolean createOrExistsDir(final String dirPath) {

        return createOrExistsDir(getFileByPath(dirPath));

    }

    /**

     * 判断目录是否存在，不存在则判断是否创建成功

     *

     * @param file 文件

     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败

     */

    public static boolean createOrExistsDir(final File file) {

        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功

        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());

    }



    /**

     * 判断文件是否存在，不存在则判断是否创建成功

     *

     * @param filePath 文件路径

     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败

     */

    public static boolean createOrExistsFile(final String filePath) {

        return createOrExistsFile(getFileByPath(filePath));

    }



    /**

     * 判断文件是否存在，不存在则判断是否创建成功

     *

     * @param file 文件

     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败

     */

    public static boolean createOrExistsFile(final File file) {

        if (file == null) return false;

        // 如果存在，是文件则返回true，是目录则返回false

        if (file.exists()) return file.isFile();

        if (!createOrExistsDir(file.getParentFile())) return false;

        try {

            return file.createNewFile();

        } catch (IOException e) {

            e.printStackTrace();

            return false;

        }

    }



    /**

     * 判断文件是否存在，存在则在创建之前删除

     *

     * @param file 文件

     * @return {@code true}: 创建成功<br>{@code false}: 创建失败

     */

    public static boolean createFileByDeleteOldFile(final File file) {

        if (file == null) return false;

        // 文件存在并且删除失败返回false

        if (file.exists() && !file.delete()) return false;

        // 创建目录失败返回false

        if (!createOrExistsDir(file.getParentFile())) return false;

        try {

            return file.createNewFile();

        } catch (IOException e) {

            e.printStackTrace();

            return false;

        }

    }



    /**

     * 复制或移动目录

     *

     * @param srcDirPath  源目录路径

     * @param destDirPath 目标目录路径

     * @param isMove      是否移动

     * @return {@code true}: 复制或移动成功<br>{@code false}: 复制或移动失败

     */

    private static boolean copyOrMoveDir(final String srcDirPath, final String destDirPath, final boolean isMove) {

        return copyOrMoveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), isMove);

    }



    /**

     * 复制或移动目录

     *

     * @param srcDir  源目录

     * @param destDir 目标目录

     * @param isMove  是否移动

     * @return {@code true}: 复制或移动成功<br>{@code false}: 复制或移动失败

     */

    private static boolean copyOrMoveDir(final File srcDir, final File destDir, final boolean isMove) {

        if (srcDir == null || destDir == null) return false;

        // 如果目标目录在源目录中则返回false，看不懂的话好好想想递归怎么结束

        // srcPath : F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res

        // destPath: F:\\MyGithub\\AndroidUtilCode\\utilcode\\src\\test\\res1

        // 为防止以上这种情况出现出现误判，须分别在后面加个路径分隔符

        String srcPath = srcDir.getPath() + File.separator;

        String destPath = destDir.getPath() + File.separator;

        if (destPath.contains(srcPath)) return false;

        // 源文件不存在或者不是目录则返回false

        if (!srcDir.exists() || !srcDir.isDirectory()) return false;

        // 目标目录不存在返回false

        if (!createOrExistsDir(destDir)) return false;

        File[] files = srcDir.listFiles();

        for (File file : files) {

            File oneDestFile = new File(destPath + file.getName());

            if (file.isFile()) {

                // 如果操作失败返回false

                if (!copyOrMoveFile(file, oneDestFile, isMove)) return false;

            } else if (file.isDirectory()) {

                // 如果操作失败返回false

                if (!copyOrMoveDir(file, oneDestFile, isMove)) return false;

            }

        }

        return !isMove || deleteDir(srcDir);

    }



    /**

     * 复制或移动文件

     *

     * @param srcFilePath  源文件路径

     * @param destFilePath 目标文件路径

     * @param isMove       是否移动

     * @return {@code true}: 复制或移动成功<br>{@code false}: 复制或移动失败

     */

    private static boolean copyOrMoveFile(final String srcFilePath, final String destFilePath, final boolean isMove) {

        return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), isMove);

    }



    /**

     * 复制或移动文件

     *

     * @param srcFile  源文件

     * @param destFile 目标文件

     * @param isMove   是否移动

     * @return {@code true}: 复制或移动成功<br>{@code false}: 复制或移动失败

     */

    private static boolean copyOrMoveFile(final File srcFile, final File destFile, final boolean isMove) {

        if (srcFile == null || destFile == null) return false;

        // 源文件不存在或者不是文件则返回false

        if (!srcFile.exists() || !srcFile.isFile()) return false;

        // 目标文件存在且是文件则返回false

        if (destFile.exists() && destFile.isFile()) return false;

        // 目标目录不存在返回false

        if (!createOrExistsDir(destFile.getParentFile())) return false;

        try {

            return IOUtils.writeFileFromIS(destFile, new FileInputStream(srcFile), false)

                    && !(isMove && !deleteFile(srcFile));

        } catch (FileNotFoundException e) {

            e.printStackTrace();

            return false;

        }

    }



    /**

     * 复制目录

     *

     * @param srcDirPath  源目录路径

     * @param destDirPath 目标目录路径

     * @return {@code true}: 复制成功<br>{@code false}: 复制失败

     */

    public static boolean copyDir(final String srcDirPath, final String destDirPath) {

        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));

    }



    /**

     * 复制目录

     *

     * @param srcDir  源目录

     * @param destDir 目标目录

     * @return {@code true}: 复制成功<br>{@code false}: 复制失败

     */

    public static boolean copyDir(final File srcDir, final File destDir) {

        return copyOrMoveDir(srcDir, destDir, false);

    }



    /**

     * 复制文件

     *

     * @param srcFilePath  源文件路径

     * @param destFilePath 目标文件路径

     * @return {@code true}: 复制成功<br>{@code false}: 复制失败

     */

    public static boolean copyFile(final String srcFilePath, final String destFilePath) {

        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));

    }



    /**

     * 复制文件

     *

     * @param srcFile  源文件

     * @param destFile 目标文件

     * @return {@code true}: 复制成功<br>{@code false}: 复制失败

     */

    public static boolean copyFile(final File srcFile, final File destFile) {

        return copyOrMoveFile(srcFile, destFile, false);

    }



    /**

     * 移动目录

     *

     * @param srcDirPath  源目录路径

     * @param destDirPath 目标目录路径

     * @return {@code true}: 移动成功<br>{@code false}: 移动失败

     */

    public static boolean moveDir(final String srcDirPath, final String destDirPath) {

        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));

    }



    /**

     * 移动目录

     *

     * @param srcDir  源目录

     * @param destDir 目标目录

     * @return {@code true}: 移动成功<br>{@code false}: 移动失败

     */

    public static boolean moveDir(final File srcDir, final File destDir) {

        return copyOrMoveDir(srcDir, destDir, true);

    }



    /**

     * 移动文件

     *

     * @param srcFilePath  源文件路径

     * @param destFilePath 目标文件路径

     * @return {@code true}: 移动成功<br>{@code false}: 移动失败

     */

    public static boolean moveFile(final String srcFilePath, final String destFilePath) {

        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));

    }



    /**

     * 移动文件

     *

     * @param srcFile  源文件

     * @param destFile 目标文件

     * @return {@code true}: 移动成功<br>{@code false}: 移动失败

     */

    public static boolean moveFile(final File srcFile, final File destFile) {

        return copyOrMoveFile(srcFile, destFile, true);

    }



    /**

     * 删除目录

     *

     * @param dirPath 目录路径

     * @return {@code true}: 删除成功<br>{@code false}: 删除失败

     */

    public static boolean deleteDir(final String dirPath) {

        return deleteDir(getFileByPath(dirPath));

    }



    /**

     * 删除目录

     *

     * @param dir 目录

     * @return {@code true}: 删除成功<br>{@code false}: 删除失败

     */

    public static boolean deleteDir(final File dir) {

        if (dir == null) return false;

        // 目录不存在返回true

        if (!dir.exists()) return true;

        // 不是目录返回false

        if (!dir.isDirectory()) return false;

        // 现在文件存在且是文件夹

        File[] files = dir.listFiles();

        if (files != null && files.length != 0) {

            for (File file : files) {

                if (file.isFile()) {

                    if (!file.delete()) return false;

                } else if (file.isDirectory()) {

                    if (!deleteDir(file)) return false;

                }

            }

        }

        return dir.delete();

    }



    /**

     * 删除文件

     *

     * @param srcFilePath 文件路径

     * @return {@code true}: 删除成功<br>{@code false}: 删除失败

     */

    public static boolean deleteFile(final String srcFilePath) {

        return deleteFile(getFileByPath(srcFilePath));

    }



    /**

     * 删除文件

     *

     * @param file 文件

     * @return {@code true}: 删除成功<br>{@code false}: 删除失败

     */

    public static boolean deleteFile(final File file) {

        return file != null && (!file.exists() || file.isFile() && file.delete());

    }



    /**

     * 删除目录下所有东西

     *

     * @param dirPath 目录路径

     * @return {@code true}: 删除成功<br>{@code false}: 删除失败

     */

    public static boolean deleteAllInDir(final String dirPath) {

        return deleteAllInDir(getFileByPath(dirPath));

    }



    /**

     * 删除目录下所有东西

     *

     * @param dir 目录

     * @return {@code true}: 删除成功<br>{@code false}: 删除失败

     */

    public static boolean deleteAllInDir(final File dir) {

        return deleteFilesInDirWithFilter(dir, new FileFilter() {

            @Override

            public boolean accept(File pathname) {

                return true;

            }

        });

    }



    /**

     * 删除目录下所有文件

     *

     * @param dirPath 目录路径

     * @return {@code true}: 删除成功<br>{@code false}: 删除失败

     */

    public static boolean deleteFilesInDir(final String dirPath) {

        return deleteFilesInDir(getFileByPath(dirPath));

    }



    /**

     * 删除目录下所有文件

     *

     * @param dir 目录

     * @return {@code true}: 删除成功<br>{@code false}: 删除失败

     */

    public static boolean deleteFilesInDir(final File dir) {

        return deleteFilesInDirWithFilter(dir, new FileFilter() {

            @Override

            public boolean accept(File pathname) {

                return pathname.isFile();

            }

        });

    }



    /**

     * 删除目录下所有过滤的文件

     *

     * @param dirPath 目录路径

     * @param filter  过滤器

     * @return {@code true}: 删除成功<br>{@code false}: 删除失败

     */

    public static boolean deleteFilesInDirWithFilter(final String dirPath, final FileFilter filter) {

        return deleteFilesInDirWithFilter(getFileByPath(dirPath), filter);

    }



    /**

     * 删除目录下所有过滤的文件

     *

     * @param dir    目录

     * @param filter 过滤器

     * @return {@code true}: 删除成功<br>{@code false}: 删除失败

     */

    public static boolean deleteFilesInDirWithFilter(final File dir, final FileFilter filter) {

        if (dir == null) return false;

        // 目录不存在返回true

        if (!dir.exists()) return true;

        // 不是目录返回false

        if (!dir.isDirectory()) return false;

        // 现在文件存在且是文件夹

        File[] files = dir.listFiles();

        if (files != null && files.length != 0) {

            for (File file : files) {

                if (filter.accept(file)) {

                    if (file.isFile()) {

                        if (!file.delete()) return false;

                    } else if (file.isDirectory()) {

                        if (!deleteDir(file)) return false;

                    }

                }

            }

        }

        return true;

    }



    /**

     * 获取目录下所有文件

     *  <p>不递归进子目录</p>

     *

     * @param dirPath 目录路径

     * @return 文件链表

     */

    public static List<File> listFilesInDir(final String dirPath) {

        return listFilesInDir(dirPath, false);

    }



    /**

     * 获取目录下所有文件

     *  <p>不递归进子目录</p>

     *

     * @param dir 目录

     * @return 文件链表

     */

    public static List<File> listFilesInDir(final File dir) {

        return listFilesInDir(dir, false);

    }



    /**

     * 获取目录下所有文件

     *

     * @param dirPath     目录路径

     * @param isRecursive 是否递归进子目录

     * @return 文件链表

     */

    public static List<File> listFilesInDir(final String dirPath, final boolean isRecursive) {

        return listFilesInDir(getFileByPath(dirPath), isRecursive);

    }



    /**

     * 获取目录下所有文件

     *

     * @param dir         目录

     * @param isRecursive 是否递归进子目录

     * @return 文件链表

     */

    public static List<File> listFilesInDir(final File dir, final boolean isRecursive) {

        return listFilesInDirWithFilter(dir, new FileFilter() {

            @Override

            public boolean accept(File pathname) {

                return true;

            }

        }, isRecursive);

    }



    /**

     * 获取目录下所有过滤的文件

     * <p>不递归进子目录</p>

     *

     * @param dirPath 目录路径

     * @param filter  过滤器

     * @return 文件链表

     */

    public static List<File> listFilesInDirWithFilter(final String dirPath,

                                                      final FileFilter filter) {

        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, false);

    }



    /**

     * 获取目录下所有过滤的文件

     * <p>不递归进子目录</p>

     *

     * @param dir    目录

     * @param filter 过滤器

     * @return 文件链表

     */

    public static List<File> listFilesInDirWithFilter(final File dir,

                                                      final FileFilter filter) {

        return listFilesInDirWithFilter(dir, filter, false);

    }



    /**

     * 获取目录下所有过滤的文件

     *

     * @param dirPath     目录路径

     * @param filter      过滤器

     * @param isRecursive 是否递归进子目录

     * @return 文件链表

     */

    public static List<File> listFilesInDirWithFilter(final String dirPath,

                                                      final FileFilter filter,

                                                      final boolean isRecursive) {

        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);

    }



    /**

     * 获取目录下所有过滤的文件

     *

     * @param dir         目录

     * @param filter      过滤器

     * @param isRecursive 是否递归进子目录

     * @return 文件链表

     */

    public static List<File> listFilesInDirWithFilter(final File dir,

                                                      final FileFilter filter,

                                                      final boolean isRecursive) {

        if (!isDir(dir)) return null;

        List<File> list = new ArrayList<>();

        File[] files = dir.listFiles();

        if (files != null && files.length != 0) {

            for (File file : files) {

                if (filter.accept(file)) {

                    list.add(file);

                }

                if (isRecursive && file.isDirectory()) {

                    //noinspection ConstantConditions

                    list.addAll(listFilesInDirWithFilter(file, filter, true));

                }

            }

        }

        return list;

    }



    /**

     * 获取文件最后修改的毫秒时间戳

     *

     * @param filePath 文件路径

     * @return 文件最后修改的毫秒时间戳

     */



    public static long getFileLastModified(final String filePath) {

        return getFileLastModified(getFileByPath(filePath));

    }



    /**

     * 获取文件最后修改的毫秒时间戳

     *

     * @param file 文件

     * @return 文件最后修改的毫秒时间戳

     */

    public static long getFileLastModified(final File file) {

        if (file == null) return -1;

        return file.lastModified();

    }



    /**

     * 简单获取文件编码格式

     *

     * @param filePath 文件路径

     * @return 文件编码

     */

    public static String getFileCharsetSimple(final String filePath) {

        return getFileCharsetSimple(getFileByPath(filePath));

    }



    /**

     * 简单获取文件编码格式

     *

     * @param file 文件

     * @return 文件编码

     */

    public static String getFileCharsetSimple(final File file) {

        int p = 0;

        InputStream is = null;

        try {

            is = new BufferedInputStream(new FileInputStream(file));

            p = (is.read() << 8) + is.read();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            CloseUtils.closeIO(is);

        }

        switch (p) {

            case 0xefbb:

                return "UTF-8";

            case 0xfffe:

                return "Unicode";

            case 0xfeff:

                return "UTF-16BE";

            default:

                return "GBK";

        }

    }



    /**

     * 获取文件行数

     *

     * @param filePath 文件路径

     * @return 文件行数

     */

    public static int getFileLines(final String filePath) {

        return getFileLines(getFileByPath(filePath));

    }



    /**

     * 获取文件行数

     * <p>比readLine要快很多</p>

     *

     * @param file 文件

     * @return 文件行数

     */

    public static int getFileLines(final File file) {

        int count = 1;

        InputStream is = null;

        try {

            is = new BufferedInputStream(new FileInputStream(file));

            byte[] buffer = new byte[1024];

            int readChars;

            if (LINE_SEP.endsWith("\n")) {

                while ((readChars = is.read(buffer, 0, 1024)) != -1) {

                    for (int i = 0; i < readChars; ++i) {

                        if (buffer[i] == '\n') ++count;

                    }

                }

            } else {

                while ((readChars = is.read(buffer, 0, 1024)) != -1) {

                    for (int i = 0; i < readChars; ++i) {

                        if (buffer[i] == '\r') ++count;

                    }

                }

            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            CloseUtils.closeIO(is);

        }

        return count;

    }



    /**

     * 获取目录大小

     *

     * @param dirPath 目录路径

     * @return 文件大小

     */

    public static String getDirSize(final String dirPath) {

        return getDirSize(getFileByPath(dirPath));

    }



    /**

     * 获取目录大小

     *

     * @param dir 目录

     * @return 文件大小

     */

    public static String getDirSize(final File dir) {

        long len = getDirLength(dir);

        return len == -1 ? "" : byte2FitMemorySize(len);

    }



    /**

     * 获取文件大小

     *

     * @param filePath 文件路径

     * @return 文件大小

     */

    public static String getFileSize(final String filePath) {

        return getFileSize(getFileByPath(filePath));

    }



    /**

     * 获取文件大小

     *

     * @param file 文件

     * @return 文件大小

     */

    public static String getFileSize(final File file) {

        long len = getFileLength(file);

        return len == -1 ? "" : byte2FitMemorySize(len);

    }



    /**

     * 获取目录长度

     *

     * @param dirPath 目录路径

     * @return 目录长度

     */

    public static long getDirLength(final String dirPath) {

        return getDirLength(getFileByPath(dirPath));

    }



    /**

     * 获取目录长度

     *

     * @param dir 目录

     * @return 目录长度

     */

    public static long getDirLength(final File dir) {

        if (!isDir(dir)) return -1;

        long len = 0;

        File[] files = dir.listFiles();

        if (files != null && files.length != 0) {

            for (File file : files) {

                if (file.isDirectory()) {

                    len += getDirLength(file);

                } else {

                    len += file.length();

                }

            }

        }

        return len;

    }



    /**

     * 获取文件长度

     *

     * @param filePath 文件路径

     * @return 文件长度

     */

    public static long getFileLength(final String filePath) {

        return getFileLength(getFileByPath(filePath));

    }



    /**

     * 获取文件长度

     *

     * @param file 文件

     * @return 文件长度

     */

    public static long getFileLength(final File file) {

        if (!isFile(file)) return -1;

        return file.length();

    }



    /**

     * 获取文件的MD5校验码

     *

     * @param filePath 文件路径

     * @return 文件的MD5校验码

     */

    public static String getFileMD5ToString(final String filePath) {

        File file = isSpace(filePath) ? null : new File(filePath);

        return getFileMD5ToString(file);

    }



    /**

     * 获取文件的MD5校验码

     *

     * @param filePath 文件路径

     * @return 文件的MD5校验码

     */

    public static byte[] getFileMD5(final String filePath) {

        File file = isSpace(filePath) ? null : new File(filePath);

        return getFileMD5(file);

    }



    /**

     * 获取文件的MD5校验码

     *

     * @param file 文件

     * @return 文件的MD5校验码

     */

    public static String getFileMD5ToString(final File file) {

        return CipherUtils.bytes2HexString(getFileMD5(file));

    }



    /**

     * 获取文件的MD5校验码

     *

     * @param file 文件

     * @return 文件的MD5校验码

     */

    public static byte[] getFileMD5(final File file) {

        if (file == null) return null;

        DigestInputStream dis = null;

        try {

            FileInputStream fis = new FileInputStream(file);

            MessageDigest md = MessageDigest.getInstance("MD5");

            dis = new DigestInputStream(fis, md);

            byte[] buffer = new byte[1024 * 256];

            while (true) {

                if (!(dis.read(buffer) > 0)) break;

            }

            md = dis.getMessageDigest();

            return md.digest();

        } catch (NoSuchAlgorithmException | IOException e) {

            e.printStackTrace();

        } finally {

            CloseUtils.closeIO(dis);

        }

        return null;

    }



    /**

     * 获取全路径中的最长目录

     *

     * @param file 文件

     * @return filePath最长目录

     */

    public static String getDirName(final File file) {

        if (file == null) return null;

        return getDirName(file.getPath());

    }



    /**

     * 获取全路径中的最长目录

     *

     * @param filePath 文件路径

     * @return filePath最长目录

     */

    public static String getDirName(final String filePath) {

        if (isSpace(filePath)) return filePath;

        int lastSep = filePath.lastIndexOf(File.separator);

        return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);

    }



    /**

     * 获取全路径中的文件名

     *

     * @param file 文件

     * @return 文件名

     */

    public static String getFileName(final File file) {

        if (file == null) return null;

        return getFileName(file.getPath());

    }



    /**

     * 获取全路径中的文件名

     *

     * @param filePath 文件路径

     * @return 文件名

     */

    public static String getFileName(final String filePath) {

        if (isSpace(filePath)) return filePath;

        int lastSep = filePath.lastIndexOf(File.separator);

        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);

    }



    /**

     * 获取全路径中的不带拓展名的文件名

     *

     * @param file 文件

     * @return 不带拓展名的文件名

     */

    public static String getFileNameNoExtension(final File file) {

        if (file == null) return null;

        return getFileNameNoExtension(file.getPath());

    }



    /**

     * 获取全路径中的不带拓展名的文件名

     *

     * @param filePath 文件路径

     * @return 不带拓展名的文件名

     */

    public static String getFileNameNoExtension(final String filePath) {

        if (isSpace(filePath)) return filePath;

        int lastPoi = filePath.lastIndexOf('.');

        int lastSep = filePath.lastIndexOf(File.separator);

        if (lastSep == -1) {

            return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));

        }

        if (lastPoi == -1 || lastSep > lastPoi) {

            return filePath.substring(lastSep + 1);

        }

        return filePath.substring(lastSep + 1, lastPoi);

    }



    /**

     * 获取全路径中的文件拓展名

     *

     * @param file 文件

     * @return 文件拓展名

     */

    public static String getFileExtension(final File file) {

        if (file == null) return null;

        return getFileExtension(file.getPath());

    }



    /**

     * 获取全路径中的文件拓展名

     *

     * @param filePath 文件路径

     * @return 文件拓展名

     */

    public static String getFileExtension(final String filePath) {

        if (isSpace(filePath)) return filePath;

        int lastPoi = filePath.lastIndexOf('.');

        int lastSep = filePath.lastIndexOf(File.separator);

        if (lastPoi == -1 || lastSep >= lastPoi) return "";

        return filePath.substring(lastPoi + 1);

    }



    ///////////////////////////////////////////////////////////////////////////

    // copy from ConvertUtils

    ///////////////////////////////////////////////////////////////////////////



    /**

     * 字节数转合适内存大小

     * <p>保留3位小数</p>

     *

     * @param byteNum 字节数

     * @return 合适内存大小

     */

    @SuppressLint("DefaultLocale")

    private static String byte2FitMemorySize(final long byteNum) {

        if (byteNum < 0) {

            return "shouldn't be less than zero!";

        } else if (byteNum < 1024) {

            return String.format("%.3fB", (double) byteNum + 0.0005);

        } else if (byteNum < 1048576) {

            return String.format("%.3fKB", (double) byteNum / 1024 + 0.0005);

        } else if (byteNum < 1073741824) {

            return String.format("%.3fMB", (double) byteNum / 1048576 + 0.0005);

        } else {

            return String.format("%.3fGB", (double) byteNum / 1073741824 + 0.0005);

        }

    }



    private static boolean isSpace(final String s) {

        if (s == null) return true;

        for (int i = 0, len = s.length(); i < len; ++i) {

            if (!Character.isWhitespace(s.charAt(i))) {

                return false;

            }

        }

        return true;

    }



    public static class IOUtils {



        private IOUtils() {

            throw new UnsupportedOperationException("u can't instantiate me...");

        }



        private static int sBufferSize = 8192;



        /**

         * 将输入流写入文件

         *

         * @param filePath 路径

         * @param is       输入流

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromIS(final String filePath, final InputStream is) {

            return writeFileFromIS(getFileByPath(filePath), is, false);

        }



        /**

         * 将输入流写入文件

         *

         * @param filePath 路径

         * @param is       输入流

         * @param append   是否追加在文件末

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromIS(final String filePath, final InputStream is, final boolean append) {

            return writeFileFromIS(getFileByPath(filePath), is, append);

        }



        /**

         * 将输入流写入文件

         *

         * @param file 文件

         * @param is   输入流

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromIS(final File file, final InputStream is) {

            return writeFileFromIS(file, is, false);

        }



        /**

         * 将输入流写入文件

         *

         * @param file   文件

         * @param is     输入流

         * @param append 是否追加在文件末

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromIS(final File file, final InputStream is, final boolean append) {

            if (!createOrExistsFile(file) || is == null) return false;

            OutputStream os = null;

            try {

                os = new BufferedOutputStream(new FileOutputStream(file, append));

                byte data[] = new byte[sBufferSize];

                int len;

                while ((len = is.read(data, 0, sBufferSize)) != -1) {

                    os.write(data, 0, len);

                }

                return true;

            } catch (IOException e) {

                e.printStackTrace();

                return false;

            } finally {

                CloseUtils.closeIO(is, os);

            }

        }



        /**

         * 将字节数组写入文件

         *

         * @param filePath 文件路径

         * @param bytes    字节数组

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes) {

            return writeFileFromBytesByStream(getFileByPath(filePath), bytes, false);

        }



        /**

         * 将字节数组写入文件

         *

         * @param filePath 文件路径

         * @param bytes    字节数组

         * @param append   是否追加在文件末

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes, final boolean append) {

            return writeFileFromBytesByStream(getFileByPath(filePath), bytes, append);

        }



        /**

         * 将字节数组写入文件

         *

         * @param file  文件

         * @param bytes 字节数组

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromBytesByStream(final File file, final byte[] bytes) {

            return writeFileFromBytesByStream(file, bytes, false);

        }



        /**

         * 将字节数组写入文件

         *

         * @param file   文件

         * @param bytes  字节数组

         * @param append 是否追加在文件末

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromBytesByStream(final File file, final byte[] bytes, final boolean append) {

            if (bytes == null || !createOrExistsFile(file)) return false;

            BufferedOutputStream bos = null;

            try {

                bos = new BufferedOutputStream(new FileOutputStream(file, append));

                bos.write(bytes);

                return true;

            } catch (IOException e) {

                e.printStackTrace();

                return false;

            } finally {

                CloseUtils.closeIO(bos);

            }

        }



        /**

         * 将字节数组写入文件

         *

         * @param filePath 文件路径

         * @param bytes    字节数组

         * @param isForce  是否写入文件

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromBytesByChannel(final String filePath, final byte[] bytes, final boolean isForce) {

            return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, false, isForce);

        }



        /**

         * 将字节数组写入文件

         *

         * @param filePath 文件路径

         * @param bytes    字节数组

         * @param append   是否追加在文件末

         * @param isForce  是否写入文件

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromBytesByChannel(final String filePath, final byte[] bytes, final boolean append, final boolean isForce) {

            return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, append, isForce);

        }



        /**

         * 将字节数组写入文件

         *

         * @param file    文件

         * @param bytes   字节数组

         * @param isForce 是否写入文件

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromBytesByChannel(final File file, final byte[] bytes, final boolean isForce) {

            return writeFileFromBytesByChannel(file, bytes, false, isForce);

        }



        /**

         * 将字节数组写入文件

         *

         * @param file    文件

         * @param bytes   字节数组

         * @param append  是否追加在文件末

         * @param isForce 是否写入文件

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromBytesByChannel(final File file, final byte[] bytes, final boolean append, final boolean isForce) {

            if (bytes == null) return false;

            FileChannel fc = null;

            try {

                fc = new FileOutputStream(file, append).getChannel();

                fc.position(fc.size());

                fc.write(ByteBuffer.wrap(bytes));

                if (isForce) fc.force(true);

                return true;

            } catch (IOException e) {

                e.printStackTrace();

                return false;

            } finally {

                CloseUtils.closeIO(fc);

            }

        }



        /**

         * 将字节数组写入文件

         *

         * @param filePath 文件路径

         * @param bytes    字节数组

         * @param isForce  是否写入文件

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromBytesByMap(final String filePath, final byte[] bytes, final boolean isForce) {

            return writeFileFromBytesByMap(filePath, bytes, false, isForce);

        }



        /**

         * 将字节数组写入文件

         *

         * @param filePath 文件路径

         * @param bytes    字节数组

         * @param append   是否追加在文件末

         * @param isForce  是否写入文件

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromBytesByMap(final String filePath, final byte[] bytes, final boolean append, final boolean isForce) {

            return writeFileFromBytesByMap(getFileByPath(filePath), bytes, append, isForce);

        }



        /**

         * 将字节数组写入文件

         *

         * @param file    文件

         * @param bytes   字节数组

         * @param isForce 是否写入文件

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromBytesByMap(final File file, final byte[] bytes, final boolean isForce) {

            return writeFileFromBytesByMap(file, bytes, false, isForce);

        }



        /**

         * 将字节数组写入文件

         *

         * @param file    文件

         * @param bytes   字节数组

         * @param append  是否追加在文件末

         * @param isForce 是否写入文件

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromBytesByMap(final File file, final byte[] bytes, final boolean append, final boolean isForce) {

            if (bytes == null || !createOrExistsFile(file)) return false;

            FileChannel fc = null;

            try {

                fc = new FileOutputStream(file, append).getChannel();

                MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, fc.size(), bytes.length);

                mbb.put(bytes);

                if (isForce) mbb.force();

                return true;

            } catch (IOException e) {

                e.printStackTrace();

                return false;

            } finally {

                CloseUtils.closeIO(fc);

            }

        }



        /**

         * 将字符串写入文件

         *

         * @param filePath 文件路径

         * @param content  写入内容

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromString(final String filePath, final String content) {

            return writeFileFromString(getFileByPath(filePath), content, false);

        }



        /**

         * 将字符串写入文件

         *

         * @param filePath 文件路径

         * @param content  写入内容

         * @param append   是否追加在文件末

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromString(final String filePath, final String content, final boolean append) {

            return writeFileFromString(getFileByPath(filePath), content, append);

        }



        /**

         * 将字符串写入文件

         *

         * @param file    文件

         * @param content 写入内容

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromString(final File file, final String content) {

            return writeFileFromString(file, content, false);

        }



        /**

         * 将字符串写入文件

         *

         * @param file    文件

         * @param content 写入内容

         * @param append  是否追加在文件末

         * @return {@code true}: 写入成功<br>{@code false}: 写入失败

         */

        public static boolean writeFileFromString(final File file, final String content, final boolean append) {

            if (file == null || content == null) return false;

            if (!createOrExistsFile(file)) return false;

            BufferedWriter bw = null;

            try {

                bw = new BufferedWriter(new FileWriter(file, append));

                bw.write(content);

                return true;

            } catch (IOException e) {

                e.printStackTrace();

                return false;

            } finally {

                CloseUtils.closeIO(bw);

            }

        }



        /**

         * 读取文件到字符串中

         *

         * @param filePath 文件路径

         * @return 字符串

         */

        public static String readFile2String(final String filePath) {

            return readFile2String(getFileByPath(filePath), null);

        }



        /**

         * 读取文件到字符串中

         *

         * @param filePath    文件路径

         * @param charsetName 编码格式

         * @return 字符串

         */

        public static String readFile2String(final String filePath, final String charsetName) {

            return readFile2String(getFileByPath(filePath), charsetName);

        }



        /**

         * 读取文件到字符串中

         *

         * @param file 文件

         * @return 字符串

         */

        public static String readFile2String(final File file) {

            return readFile2String(file, null);

        }



        /**

         * 读取文件到字符串中

         *

         * @param file        文件

         * @param charsetName 编码格式

         * @return 字符串

         */

        public static String readFile2String(final File file, final String charsetName) {

            if (!isFileExists(file)) return null;

            BufferedReader reader = null;

            try {

                StringBuilder sb = new StringBuilder();

                if (isSpace(charsetName)) {

                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

                } else {

                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));

                }

                String line;

                if ((line = reader.readLine()) != null) {

                    sb.append(line);

                    while ((line = reader.readLine()) != null) {

                        sb.append(LINE_SEP).append(line);

                    }

                }

                return sb.toString();

            } catch (IOException e) {

                e.printStackTrace();

                return null;

            } finally {

                CloseUtils.closeIO(reader);

            }

        }



        /**

         * 读取文件到字节数组中

         *

         * @param filePath 文件路径

         * @return 字符数组

         */

        public static byte[] readFile2BytesByStream(final String filePath) {

            return readFile2BytesByStream(getFileByPath(filePath));

        }



        /**

         * 读取文件到字节数组中

         *

         * @param file 文件

         * @return 字符数组

         */

        public static byte[] readFile2BytesByStream(final File file) {

            if (!isFileExists(file)) return null;

            FileInputStream fis = null;

            ByteArrayOutputStream os = null;

            try {

                fis = new FileInputStream(file);

                os = new ByteArrayOutputStream();

                byte[] b = new byte[sBufferSize];

                int len;

                while ((len = fis.read(b, 0, sBufferSize)) != -1) {

                    os.write(b, 0, len);

                }

                return os.toByteArray();

            } catch (IOException e) {

                e.printStackTrace();

                return null;

            } finally {

                CloseUtils.closeIO(fis, os);

            }

        }



        /**

         * 读取文件到字节数组中

         *

         * @param filePath 文件路径

         * @return 字符数组

         */

        public static byte[] readFile2BytesByChannel(final String filePath) {

            return readFile2BytesByChannel(getFileByPath(filePath));

        }



        /**

         * 读取文件到字节数组中

         *

         * @param file 文件

         * @return 字符数组

         */

        public static byte[] readFile2BytesByChannel(final File file) {

            if (!isFileExists(file)) return null;

            FileChannel fc = null;

            try {

                fc = new RandomAccessFile(file, "r").getChannel();

                ByteBuffer byteBuffer = ByteBuffer.allocate((int) fc.size());

                while (true) {

                    if (!((fc.read(byteBuffer)) > 0)) break;

                }

                return byteBuffer.array();

            } catch (IOException e) {

                e.printStackTrace();

                return null;

            } finally {

                CloseUtils.closeIO(fc);

            }

        }



        /**

         * 读取文件到字节数组中

         *

         * @param filePath 文件路径

         * @return 字符数组

         */

        public static byte[] readFile2BytesByMap(final String filePath) {

            return readFile2BytesByMap(getFileByPath(filePath));

        }



        /**

         * 读取文件到字节数组中

         *

         * @param file 文件

         * @return 字符数组

         */

        public static byte[] readFile2BytesByMap(final File file) {

            if (!isFileExists(file)) return null;

            FileChannel fc = null;

            try {

                fc = new RandomAccessFile(file, "r").getChannel();

                int size = (int) fc.size();

                MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size).load();

                byte[] result = new byte[size];

                mbb.get(result, 0, size);

                return result;

            } catch (IOException e) {

                e.printStackTrace();

                return null;

            } finally {

                CloseUtils.closeIO(fc);

            }

        }



        /**

         * 设置缓冲区尺寸

         *

         * @param bufferSize 缓冲区大小

         */

        public static void setBufferSize(final int bufferSize) {

            sBufferSize = bufferSize;

        }



    }



    private static class CloseUtils {

        private CloseUtils() {

            throw new UnsupportedOperationException("u can't instantiate me...");

        }


        /**

         * 关闭IO

         *

         * @param closeables closeables

         */

        public static void closeIO(final Closeable... closeables) {

            if (closeables == null) return;

            for (Closeable closeable : closeables) {

                if (closeable != null) {

                    try {

                        closeable.close();

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                }

            }

        }



        /**

         * 安静关闭IO

         *

         * @param closeables closeables

         */

        public static void closeIOQuietly(final Closeable... closeables) {

            if (closeables == null) return;

            for (Closeable closeable : closeables) {

                if (closeable != null) {

                    try {

                        closeable.close();

                    } catch (IOException ignored) {

                    }

                }

            }

        }

    }



}