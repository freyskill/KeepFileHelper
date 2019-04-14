package top.keepempty.file;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auth: frey tse
 */
public class KFUtils {

    public boolean isDebug = false;

    public static final int INT = 1;

    public static final int EXT = 2;

    public static final int SYS = 3;

    public static final String SEPARATOR = "/";
    public static final String DOT = ".";

    private boolean lastIsFile = false;

    private Context context;

    public KFUtils(Context context) {
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
    public String getInternalDir4Type(int type) {
        if (type == KFConstants.ROOT) {
            return context.getFilesDir().getParent();
        } else if (type == KFConstants.CACHE) {
            return context.getCacheDir().getAbsolutePath();
        } else if (type == KFConstants.FILES) {
            return context.getFilesDir().getAbsolutePath();
        }
        return null;
    }


    /**
     * 在应用外部存储创建
     *
     * @param type 内部或外部存储
     * @return
     */
    public String getExternalDir4Type(int type) {
        if (type == KFConstants.ROOT) {
            return context.getExternalFilesDir(null).getParent();
        } else if (type == KFConstants.CACHE) {
            return context.getExternalCacheDir().getAbsolutePath();
        } else if (type == KFConstants.FILES) {
            return context.getExternalFilesDir(null).getAbsolutePath();
        }
        return null;
    }

    /**
     * 在外部存储根目录创建
     *
     * @return
     */
    public File createInSystemRoot() {
        if (!isSDCardEnableByEnvironment()) {
            return null;
        }
        if (!isUsableSpace4Ext()) {
            return null;
        }
        return Environment.getExternalStoragePublicDirectory(null).getParentFile();
    }


    /**
     * 创建文件夹或者文件
     *
     * @param type
     * @param where
     * @param dirOrFileName
     * @return
     */
    public File existDirsOrFile(int type, int where, String dirOrFileName) {
        // 判断存储位置
        String root = getRootByType(type, where);
        if (TextUtils.isEmpty(dirOrFileName)) {
            return new File(root);
        }
        dirOrFileName.replace(" ", "");
        if (dirOrFileName.endsWith(".")) {
            dirOrFileName = dirOrFileName.substring(0, dirOrFileName.length() - 2);
        }

        String path = dirOrFileName.startsWith(SEPARATOR) ?
                root + dirOrFileName : root + File.separator + dirOrFileName;


        String[] pathAndFileName = getPathAndFileName(path);
        String filePathStr = pathAndFileName[0];
        // 创建目录
        File filePath = new File(filePathStr);
        if(!filePath.exists()){
            boolean mkdirs = filePath.mkdirs();
            if(mkdirs){
                if(isDebug) KFLog.i("目录创建成功："+filePath.getAbsolutePath());
            }else{
                if(isDebug) KFLog.d("目录创建失败！");
            }
        }

        String fileName = pathAndFileName[1];
        // 创建文件
        if(fileName.contains(".") || lastIsFile){
            return createFile(filePath.getAbsolutePath(), fileName);
        }else {
            // 创建目录
            File file = new File(filePath.getAbsoluteFile()+File.separator+fileName);
            return file.exists() ? file.getAbsoluteFile() : null;
        }
    }

    /**
     * 获取创建位置
     *
     * @param type  （内部，外部，根目录）
     * @param where 存储位置（files，cache）
     * @return 路径
     */
    private String getRootByType(int type, int where) {
        String root;
        if (where == INT) {
            root = getInternalDir4Type(type);
        } else if (where == EXT) {
            root = getExternalDir4Type(type);
        } else {
            root = createInSystemRoot().getAbsolutePath();
        }
        return root;
    }

    /**
     * 创建文件
     *
     * @param dir      创建目录
     * @param fileName 文件名称
     * @return
     */
    private File createFile(String dir, String fileName) {
        File file = new File(dir, fileName);
        try {
            boolean newFile = file.createNewFile();
            if (newFile) {
                if (isDebug) {
                    KFLog.i("文件创建成功，文件路径：" + file.getAbsolutePath());
                }
                return file.getAbsoluteFile();
            }
        } catch (IOException e) {
            if (isDebug) {
                KFLog.e("文件创建失败，error:" + e.getMessage());
            }
            e.printStackTrace();
        }
        return new File(dir);
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


    public static void main(String[] args) {

        //String tet = "sdf/sdfa/sdfa2/es/asfdNi/t.t";

        String str = "c:\\demo\\test.txt";
        String str2 = "/data/data/com.demo.test/files/test/tx.t";
        //System.out.println("name:" + getPathAndFileName(str2));
        System.out.println(getPathAndFileName(str2)[0]+"--"+getPathAndFileName(str2)[1]);
    }

    /**
     * 根据路径获取文件名称
     *
     * @param fullPath 完整路径
     * @return String[] 文件路径、文件名称
     */
    private static String[] getPathAndFileName(String fullPath) {
        String reg = "((/.*/)|(/))([^/]+$)";
        Matcher m = Pattern.compile(reg).matcher(fullPath);
        String path = null;
        String filename = null;
        if (m.find()) {
            path = m.group(1); // 文件路径
            filename = m.group(m.groupCount()); //文件名称
        }
        return new String[]{path,filename};
    }

}
