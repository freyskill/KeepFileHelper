package top.keepempty.file;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;

import top.keepempty.file.utils.KFUtils;

/**
 * 1.创建本地文件目录
 * 2.获取指定目录文件
 * @auth: frey tse
 */
public class KeepFileManager {

    private Context mContext;

    private static KeepFileManager keepFileManager;

    private boolean isInternal = false;

    private KeepFileManager(Context context){
        this.mContext = context;
    }

    public static KeepFileManager init(Context context){
        if(keepFileManager==null){
            return new KeepFileManager(context.getApplicationContext());
        }
        return keepFileManager;
    }

    /**
     * 设置保存到内部存储
     * @return
     */
    public KeepFileManager toInternal4App(){
        this.isInternal = true;
        return keepFileManager;
    }


    public KeepFileManager toExternalRoot(){
        return keepFileManager;
    }


    /**
     * 在应用根目录创建
     * 创建目录：/data/user/0/你的包名 （内部存储）
     *         /storage/emulated/0/Android/data/你的包名 （外部存储）
     * @param dirName 需要创建的目录
     * @return
     */
    public File createDirInRoot(String dirName){
        if(isInternal && KFUtils.isUsableSpace4Int(mContext)){
           return existDirs(KFUtils.getInternalDir4Type(mContext,KFConstants.ROOT), dirName);
        }
        return existDirs(KFUtils.getExternalDir4Type(mContext,KFConstants.ROOT), dirName);
    }

    /**
     * /data/user/0/你的包名/files
     * @param dirName
     * @return
     */
    public File createDirInFiles(String dirName){
        if(isInternal){
            return existDirs(KFUtils.getInternalDir4Type(mContext,KFConstants.ROOT), dirName);
        }
        return existDirs(KFUtils.getExternalDir4Type(mContext,KFConstants.ROOT), dirName);
    }




    private File existDirs(String root, String dirs){
        if(TextUtils.isEmpty(dirs)){
            return new File(root);
        }
        String path;
        if(dirs.startsWith("/")){
            path = root+dirs;
        }else{
            path = root + File.separator + dirs;
        }
        File file = new File(path);
        if(!file.exists()){
            file.exists();
        }
        return file.getAbsoluteFile();
    }
}
