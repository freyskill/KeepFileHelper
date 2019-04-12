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
     * 设置保存到内部存储
     * @return
     */
    public KeepFileHelper toInternal(){
        this.isInternal = true;
        return this;
    }

    public KeepFileHelper showLog(){
        kfUtils.setDebug(true);
        return this;
    }

    /**
     * 在应用录创建，通过type指定默认的files、cache目录
     * @param dirName 需要创建的目录
     * @param type 指定目录类型
     *             （KFConstants.ROOT,
     *              KFConstants.CACHE,
     *              KFConstants.FILES）
     * @return 创建的目录：/data/user/0/你的包名 + type + dirName（内部存储）
     *                   /storage/emulated/0/Android/data/你的包名 + type + dirName（外部存储）
     */
    public File createDirInAppPkg(String dirName, int type){
        if(isInternal && kfUtils.isUsableSpace4Int()){
           return kfUtils.existDirs(type,KFUtils.INT, dirName);
        }
        return kfUtils.existDirs(type,KFUtils.EXT, dirName);
    }

    /**
     * 在应用根目录创建
     * @param dirName 需要创建的目录
     * @return 创建的目录：/data/user/0/你的包名+dirName （内部存储）
     *                 /storage/emulated/0/Android/data/你的包名 + dirName （外部存储）
     */
    public File createDirInAppPkg(String dirName){
        return createDirInAppPkg(dirName,KFConstants.ROOT);
    }

    /**
     * 在外部存储的根目录创建文件夹
     * @param dirName
     * @return
     */
    public File createDirInSysteRoot(String dirName){
        return kfUtils.existDirs(0,KFUtils.SYS, dirName);
    }




}
