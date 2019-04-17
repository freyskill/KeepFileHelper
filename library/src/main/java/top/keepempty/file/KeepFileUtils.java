package top.keepempty.file;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : frey tse
 * @date : 2019-03-27
 */
public final class KeepFileUtils {

    boolean isDebug = false;

    static final int INT = 1;

    static final int EXT = 2;

    static final int PUBLIC = 3;

    static final String SEPARATOR = "/";
    static final String DOT = ".";

    /**
     * 将最后的目录名称做为文件名称进行创建
     */
    private boolean lastIsFile = false;
    /**
     * 是否替换同名文件
     */
    private boolean isReplace = false;

    private Context context;

    public KeepFileUtils(Context context) {
        this.context = context;
    }

    public boolean isSDCardEnableByEnvironment() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 在应用内部存储创建
     *
     * @param type
     * @return
     */
    public String getInternalDir4Type(String type) {
        if (type.equals(KeepFileConstants.CACHE)) {
            return context.getCacheDir().getAbsolutePath();
        } else if (type.equals(KeepFileConstants.FILES)) {
            return context.getFilesDir().getAbsolutePath();
        }
        return context.getFilesDir().getParent();
    }


    /**
     * 在应用外部存储创建
     *
     * @param type 内部或外部存储
     * @return
     */
    public String getExternalDir4Type(String type) {
        if (type == KeepFileConstants.CACHE) {
            return context.getExternalCacheDir().getAbsolutePath();
        } else if (type == KeepFileConstants.FILES) {
            return context.getExternalFilesDir(null).getAbsolutePath();
        }
        return context.getExternalFilesDir(null).getParent();
    }

    /**
     * 创建文件夹或者文件
     *
     * @param type
     * @param where
     * @param dirOrFileName
     * @return
     */
    public KeepFile existDirsOrFile(String type, int where, String dirOrFileName) {
        // 判断存储位置
        String root = getRootByType(type, where);
        if (TextUtils.isEmpty(dirOrFileName)) {
            return new KeepFile(new File(root));
        }
        dirOrFileName = dirOrFileName.replace(" ", "");
        if (dirOrFileName.endsWith(DOT)) {
            dirOrFileName = dirOrFileName.substring(0, dirOrFileName.length() - 2);
        }
        // 拼接路径
        String path = dirOrFileName.startsWith(SEPARATOR) ?
                root + dirOrFileName : root + File.separator + dirOrFileName;
        // 获取文件路径和名称
        String[] pathAndFileName = getPathAndFileName(path);
        String filePathStr = pathAndFileName[0];
        // 创建目录
        File filePath = new File(filePathStr);
        filePath.mkdirs();
        String fileName = pathAndFileName[1];
        // 创建文件
        if (fileName.contains(DOT) || lastIsFile) {
            return createFile(filePath.getAbsolutePath(), fileName);
        } else {
            return createDir(filePath.getAbsolutePath(), fileName);
        }
    }


    /**
     * 获取创建位置
     *
     * @param type  （内部，外部，根目录）
     * @param where 存储位置（files，cache）
     * @return 路径
     */
    private String getRootByType(String type, int where) {
        String root;
        if (where == INT) {
            root = getInternalDir4Type(type);
        } else if (where == EXT) {
            root = getExternalDir4Type(type);
        } else {
            root = createInPublic4Type(type).getAbsolutePath();
        }
        return root;
    }

    /**
     * 在外部存储根目录创建
     *
     * @return
     */
    public File createInPublic4Type(String type) {
        if (!isSDCardEnableByEnvironment()) {
            return null;
        }
        if (!isUsableSpace4Ext()) {
            return null;
        }
        return type.equals(KeepFileConstants.ROOT) ?
                Environment.getRootDirectory().getAbsoluteFile() :
                Environment.getExternalStoragePublicDirectory(type).getAbsoluteFile();
    }

    /**
     * 创建目录
     *
     * @param path    当前目录
     * @param dirName 需要创建的目录名称
     * @return 创建的目录
     */
    private KeepFile createDir(String path, String dirName) {
        // 创建目录
        File file = new File(path + File.separator + dirName);
        if (file.exists()) {
            if (isDebug) {
                KeepFileLog.i("目录创建成功：" + file.getAbsolutePath());
            }
            return new KeepFile(file.getAbsoluteFile());
        } else {
            if (isDebug) {
                KeepFileLog.d("目录创建失败！");
            }
            return new KeepFile(file.getAbsoluteFile());
        }
    }

    /**
     * 创建文件
     *
     * @param dir      创建目录
     * @param fileName 文件名称
     * @return
     */
    private KeepFile createFile(String dir, String fileName) {
        File file = new File(dir, fileName);
        try {
            // 如果已经创建则删除重新创建
            if (file.exists() && isReplace) {
                file.delete();
            }
            boolean newFile = file.createNewFile();
            if (newFile) {
                if (isDebug) {
                    KeepFileLog.i("文件创建成功，文件路径：" + file.getAbsolutePath());
                }
                return new KeepFile(file.getAbsoluteFile());
            }
        } catch (IOException e) {
            if (isDebug) {
                KeepFileLog.e("文件创建失败，error:" + e.getMessage());
            }
            e.printStackTrace();
            return new KeepFile(new File(dir));
        }
        return new KeepFile(new File(dir));
    }

    /**
     * 删除文件夹、文件
     *
     * @param dirOrFile 删除的目录、文件夹或文件
     * @return 是否成功删除
     */
    public boolean delDirOrFile(File dirOrFile) {
        if (dirOrFile.exists()) {
            return dirOrFile.delete();
        }
        return true;
    }


    /**
     * 判断存储空间
     *
     * @return
     */
    public boolean isUsableSpace4Int() {
        return context.getCacheDir().getParentFile().getUsableSpace() > 1;
    }

    /**
     * 判断存储空间
     *
     * @return
     */
    public boolean isUsableSpace4Ext() {
        return Environment.getDataDirectory().getUsableSpace() > 1;
    }


    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**
     * 设置最后名称为文件名称，例如：/files/test test为需创建的文件名称
     *
     * @param lastIsFile 是否将最后 “/”后面的名称做为文件创建
     *                   true：是 默认：false
     */
    public void setLastIsFile(boolean lastIsFile) {
        this.lastIsFile = lastIsFile;
    }

    /**
     * 创建同名文件的时候是否替换现有文件
     *
     * @return
     */
    public void setReplace(boolean replace) {
        this.isReplace = replace;
    }

    /**
     * 根据路径获取文件名称
     *
     * @param fullPath 完整路径
     * @return String[] 文件路径、文件名称
     */
    private static String[] getPathAndFileName(String fullPath) {
        String reg = "((/.*/)|(/))([^/]+$)";
        Matcher m = Pattern.compile(reg, Pattern.CASE_INSENSITIVE).matcher(fullPath);
        String path = null;
        String filename = null;
        if (m.find()) {
            // 文件路径
            path = m.group(1);
            //文件名称
            filename = m.group(m.groupCount());
        }
        return new String[]{path, filename};
    }

}
