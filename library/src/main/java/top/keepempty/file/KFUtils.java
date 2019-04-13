package top.keepempty.file;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

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

    public KFUtils(Context context){
        this.context = context;
    }

    public boolean isSDCardEnableByEnvironment(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 在应用内部存储创建
     * @param type
     * @return
     */
    public  String getInternalDir4Type(int type){
        if(type == KFConstants.ROOT){
            return context.getFilesDir().getParent();
        }else if(type == KFConstants.CACHE){
            return context.getCacheDir().getAbsolutePath();
        }else if(type == KFConstants.FILES){
            return context.getFilesDir().getAbsolutePath();
        }
        return null;
    }


    /**
     * 在应用外部存储创建
     * @param type 内部或外部存储
     * @return
     */
    public  String getExternalDir4Type(int type){
        if(type == KFConstants.ROOT){
            return context.getExternalFilesDir(null).getParent();
        }else if(type == KFConstants.CACHE){
            return context.getExternalCacheDir().getAbsolutePath();
        }else if(type == KFConstants.FILES){
            return context.getExternalFilesDir(null).getAbsolutePath();
        }
        return null;
    }

    /**
     * 在外部存储根目录创建
     * @return
     */
    public  File createInSystemRoot(){
        if(!isSDCardEnableByEnvironment()){
            return null;
        }
        if(!isUsableSpace4Ext()){
            return null;
        }
        return Environment.getExternalStoragePublicDirectory(null).getParentFile();
    }


    /**
     * 创建文件夹或者文件
     * @param type
     * @param where
     * @param dirOrFileName
     * @return
     */
    public File existDirsOrFile(int type, int where, String dirOrFileName){
        // 判断存储位置
        String root = getRootByType(type, where);
        if(TextUtils.isEmpty(dirOrFileName)){
            return new File(root);
        }

        String path = null;
        String fileName = null;
        int index = dirOrFileName.lastIndexOf(DOT);
        // 是否为文件
        if( index != -1){
            path = dirOrFileName.substring(0, index-1);
            fileName = getFileName(path);
        }else if (lastIsFile){
            // 将最后的名称做为文件名称进行创建
            int i = dirOrFileName.lastIndexOf(SEPARATOR);
            if(i !=-1){
                path = dirOrFileName.substring(0,i-1);
                fileName = path.substring(i, dirOrFileName.length()-1);
            }
        }

        // 处理路径
        path = path.startsWith(SEPARATOR) ?
                root+path : root + File.separator + path;

        // 创建目录 或 文件
        File file = new File(path);
        if(!file.exists()){
            boolean exists = file.exists();
            if(exists && !TextUtils.isEmpty(fileName)){
                return createFile(path,fileName);
            }else if(exists && isDebug){
                KFLog.i("目录创建成功,路径："+path);
            }
            return file.exists() ? file.getAbsoluteFile() : null;
        }
        return file.getAbsoluteFile();
    }

    /**
     * 获取创建位置
     * @param type （内部，外部，根目录）
     * @param where 存储位置（files，cache）
     * @return 路径
     */
    private String getRootByType(int type, int where){
        String root;
        if(where==INT){
            root = getInternalDir4Type(type);
        }else if(where==EXT){
            root = getExternalDir4Type(type);
        }else {
            root = createInSystemRoot().getAbsolutePath();
        }
        return root;
    }

    /**
     * 创建文件
     * @param dir 创建目录
     * @param fileName 文件名称
     * @return
     */
    private File createFile(String dir, String fileName){
        File file = new File(dir,fileName);
        try {
            boolean newFile = file.createNewFile();
            if(newFile){
                if(isDebug){
                    KFLog.i("文件创建成功，文件路径："+file.getAbsolutePath());
                }
                return file.getAbsoluteFile();
            }
        } catch (IOException e) {
            if(isDebug){
                KFLog.e("文件创建失败，error:"+e.getMessage());
            }
            e.printStackTrace();
        }
        return new File(dir);
    }

    /**
     * 根据路径获取文件名称
     * @param path 路径
     * @return 文件名称
     */
    private String getFileName(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        int i = path.lastIndexOf(SEPARATOR);
        if( i != -1){
            return path.substring(i,path.length()-1);
        }
        return null;
    }

    /**
     * 删除文件夹、文件
     * @param dirOrFile 删除的目录、文件夹或文件
     * @return 是否成功删除
     */
    public boolean delDirOrFile(File dirOrFile){
        if(dirOrFile.exists()){
           return dirOrFile.delete();
        }
        return true;
    }


    /**
     * 判断存储空间
     * @return
     */
    public  boolean isUsableSpace4Int(){
        return context.getCacheDir().getParentFile().getUsableSpace()>1;
    }

    /**
     * 判断存储空间
     * @return
     */
    public  boolean isUsableSpace4Ext(){
        return Environment.getDataDirectory().getUsableSpace()>1;
    }


    public void setDebug(boolean isDebug){
        this.isDebug = isDebug;
    }

    /**
     * 设置最后名称为文件名称，例如：/files/test test为需创建的文件名称
     * @param lastIsFile 是否将最后 “/”后面的名称做为文件创建
     *                   true：是 默认：false
     */
    public void setLastIsFile(boolean lastIsFile) {
        this.lastIsFile = lastIsFile;
    }
}
