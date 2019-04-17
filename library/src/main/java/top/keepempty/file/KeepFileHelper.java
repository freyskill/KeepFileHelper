package top.keepempty.file;

import android.content.Context;

import java.io.File;
import java.lang.ref.WeakReference;

import top.keepempty.file.permsn.KeepPermissionUtil;
import top.keepempty.file.permsn.OnKeepPermsnCallback;

/**
 * 1.创建本地文件目录
 * 2.获取指定目录文件
 *
 * @auth: frey tse
 */
public final class KeepFileHelper {

    private Context mContext;

    private static KeepFileHelper keepFileHelper;
    /**
     * 是否创建在内部存储
     */
    private boolean isInternal = false;

    private KeepFileUtils keepFileUtils;

    static WeakReference<KeepFileHelper> weakReference;

    private OnKeepPermsnCallback onPermissionCallback;

    private KeepFileHelper(Context context) {
        this.mContext = context;
        keepFileUtils = new KeepFileUtils(context);
    }

    public static KeepFileHelper init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The context can't be null!");
        }
        if(weakReference==null){
            weakReference = new WeakReference<>(new KeepFileHelper(context));
        }
        return weakReference.get() == null ? new KeepFileHelper(context) : weakReference.get();
    }

    /**
     * 指定文件夹或文件创建在内部存储，默认为外部存储
     * 内部存储只能当前应用能够访问
     * 外部存储其他应用都可以访问
     *
     * @return KeepFileHelper
     */
    public KeepFileHelper toInternal() {
        this.isInternal = true;
        return this;
    }

    /**
     * 是否显示日志
     *
     * @return KeepFileHelper
     */
    public KeepFileHelper showLog() {
        keepFileUtils.setDebug(true);
        return this;
    }

    /**
     * 将路径最后的名称创建为文件，
     * 例如：/files/test test为创建的文件名称
     *
     * @return KeepFileHelper
     */
    public KeepFileHelper lastIsFile() {
        keepFileUtils.setLastIsFile(true);
        return this;
    }

    /**
     * 创建同名文件的时候是否替换现有文件,
     * 默认不进行替换，保持原有文件
     *
     * @return KeepFileHelper
     */
    public KeepFileHelper replaceFile() {
        keepFileUtils.setReplace(true);
        return this;
    }


    public KeepFileHelper showPermissionTips(){
        return this;
    }

    public KeepFileHelper setOnPermissionCallback(OnKeepPermsnCallback onPermissionCallback){
        this.onPermissionCallback = onPermissionCallback;
        return this;
    }

    /**
     * 在应用根目录创建新的文件夹或文件，可以通过指定type创建在files或cache目录。
     * 在此目录创建的文件夹、文件，当应用卸载时，会将目录一并删除
     *
     * @param dirName 需要创建的文件夹、文件名称
     * @param type    指定目录类型（KeepFileConstants.ROOT, KeepFileConstants.CACHE, KeepFileConstants.FILES）
     * @return 创建的目录路径，例如：/data/user/0/你的包名 + type + dirOrFileName（内部存储）
     * /storage/emulated/0/Android/data/你的包名 + type + dirOrFileName（外部存储）
     */
    public KeepFile createInAppPkg(String dirName, String type) {
        if (isInternal) {
            return keepFileUtils.existDirsOrFile(type, KeepFileUtils.INT, dirName);
        }
        return keepFileUtils.existDirsOrFile(type, KeepFileUtils.EXT, dirName);
    }

    /**
     * 在应用根目录创建新的文件夹或文件。
     * 在此目录创建的文件夹、文件，当应用卸载时，会将目录一并删除
     *
     * @param dirOrFileName 需要创建的文件夹、文件名称
     * @return 创建的目录路径，例如：/data/user/0/你的包名 + type + dirOrFileName（内部存储）
     * /storage/emulated/0/Android/data/你的包名 + type + dirOrFileName（外部存储）
     */
    public KeepFile createInAppPkg(String dirOrFileName) {
        return createInAppPkg(dirOrFileName, KeepFileConstants.ROOT);
    }

    /**
     * 在公开的根目录创建文件夹，
     * 在此目录创建的文件夹、文件，应用卸载时不会删除
     *
     * @param dirOrFileName 文件夹、文件名称
     * @return 创建的目录路径
     */
    public KeepFile createInPublic(String dirOrFileName) {
       return createInPublic(dirOrFileName, KeepFileConstants.ROOT);
    }

    /**
     * 在公开的根目录创建文件夹，
     * 在此目录创建的文件夹、文件，应用卸载时不会删除
     *
     * @param dirOrFileName 文件夹、文件名称
     * @param type          Environment公开文件类型：
     *                      <p>
     *                      DIRECTORY_MUSIC
     *                      DIRECTORY_PODCASTS
     *                      DIRECTORY_ALARMS
     *                      DIRECTORY_RINGTONES
     *                      DIRECTORY_NOTIFICATIONS
     *                      DIRECTORY_PICTURES
     *                      DIRECTORY_MOVIES
     *                      DIRECTORY_DOWNLOADS
     *                      DIRECTORY_DCIM
     *                      DIRECTORY_DOCUMENTS
     * @return 创建的目录路径
     */
    public KeepFile createInPublic(String dirOrFileName, String type) {
        if(KeepPermissionUtil.verifyPermissions(mContext)){
            return keepFileUtils.existDirsOrFile(type, KeepFileUtils.PUBLIC, dirOrFileName);
        }
        return null;
    }


    /**
     * 删除目录、文件夹或者文件
     *
     * @param dirsOrFile 需要删除的目录、文件夹或文件File对象
     * @return 是否删除成功
     */
    public boolean deleteDirOrFile(File dirsOrFile) {
        return keepFileUtils.delDirOrFile(dirsOrFile);
    }

    /**
     * 删除目录、文件夹或者文件
     *
     * @param dirsOrFile 需要删除的目录、文件夹或文件路径
     * @return 是否删除成功
     */
    public boolean deleteDirOrFile(String dirsOrFile) {
        File file = new File(dirsOrFile);
        return keepFileUtils.delDirOrFile(file);
    }

    /**
     * 删除指定位置的文件夹
     *
     * @param location
     * @param dirName
     * @return
     */
    public boolean deleteDir(int location, String dirName) {
        return false;
    }

}
