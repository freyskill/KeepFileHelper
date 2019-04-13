package top.keepempty.file;

import android.content.Context;

import java.io.File;

/**
 * 1.创建本地文件目录
 * 2.获取指定目录文件
 * @auth: frey tse
 */
public class KeepFileHelper {

    private Context mContext;

    private static KeepFileHelper keepFileHelper;

    private boolean isInternal = false;

    private KFUtils kfUtils;

    private KeepFileHelper(Context context){
        this.mContext = context;
        kfUtils = new KFUtils(context);
    }

    public static KeepFileHelper init(Context context){
        if(context==null){
            throw new IllegalArgumentException("The context can't be null!");
        }
        if(keepFileHelper ==null){
            return new KeepFileHelper(context.getApplicationContext());
        }
        return keepFileHelper;
    }

    /**
     * 指定文件夹或文件创建在内部存储，默认为外部存储
     * @return KeepFileHelper
     */
    public KeepFileHelper toInternal(){
        this.isInternal = true;
        return this;
    }

    /**
     * 是否显示日志
     * @return KeepFileHelper
     */
    public KeepFileHelper showLog(){
        kfUtils.setDebug(true);
        return this;
    }

    /**
     * 将路径最后的名称创建为文件，
     * 例如：/files/test test为创建的文件名称
     * @return
     */
    public KeepFileHelper lastIsFile(){
        kfUtils.setLastIsFile(true);
        return this;
    }

    /**
     * 在应用根目录创建新的文件夹或文件，可以通过指定type创建在files或cache目录
     * @param dirName 需要创建的文件夹、文件名称
     * @param type 指定目录类型（KFConstants.ROOT, KFConstants.CACHE, KFConstants.FILES）
     * @return 创建的目录路径，例如：/data/user/0/你的包名 + type + dirOrFileName（内部存储）
     *                   /storage/emulated/0/Android/data/你的包名 + type + dirOrFileName（外部存储）
     */
    public File createInAppPkg(String dirName, int type){
        if(isInternal && kfUtils.isUsableSpace4Int()){
           return kfUtils.existDirsOrFile(type,KFUtils.INT, dirName);
        }
        return kfUtils.existDirsOrFile(type,KFUtils.EXT, dirName);
    }

    /**
     * 在应用根目录创建新的文件夹或文件
     * @param dirOrFileName 需要创建的文件夹、文件名称
     * @return 创建的目录路径，例如：/data/user/0/你的包名 + type + dirOrFileName（内部存储）
     *                   /storage/emulated/0/Android/data/你的包名 + type + dirOrFileName（外部存储）
     */
    public File createInAppPkg(String dirOrFileName){
        return createInAppPkg(dirOrFileName,KFConstants.ROOT);
    }

    /**
     * 在外部存储的根目录创建文件夹
     * @param dirOrFileName 文件夹、文件名称
     * @return 创建的目录路径
     */
    public File createInSysteRoot(String dirOrFileName){
        return kfUtils.existDirsOrFile(0,KFUtils.SYS, dirOrFileName);
    }

    /**
     * 删除目录、文件夹或者文件
     * @param dirsOrFile 需要删除的目录、文件夹或文件File对象
     * @return 是否删除成功
     */
    public boolean deleteDirOrFile(File dirsOrFile){
        return kfUtils.delDirOrFile(dirsOrFile);
    }

    /**
     * 删除目录、文件夹或者文件
     * @param dirsOrFile 需要删除的目录、文件夹或文件路径
     * @return 是否删除成功
     */
    public boolean deleteDirOrFile(String dirsOrFile){
        File file = new File(dirsOrFile);
        return kfUtils.delDirOrFile(file);
    }

    /**
     * 删除指定位置的文件夹
     * @param location
     * @param dirName
     * @return
     */
    public boolean deleteDir(int location,String dirName){
        return false;
    }

}
