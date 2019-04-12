package top.keepempty.file.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

import top.keepempty.file.KFConstants;
import top.keepempty.file.KFLog;

/**
 * @auth: frey tse
 */
public class KFUtils {

    public boolean isDebug = false;

    public static final int INT = 1;

    public static final int EXT = 2;

    public static final int SYS = 3;

    private Context context;

    public KFUtils(Context context){
        this.context = context;
    }

    public boolean isSDCardEnableByEnvironment(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public  String getInternalDir4Type(int type){
        if(type == KFConstants.ROOT){
            if(isDEBUG()){
                KFLog.i("");
            }
            return context.getFilesDir().getParent();
        }else if(type == KFConstants.CACHE){
            return context.getCacheDir().getAbsolutePath();
        }else if(type == KFConstants.FILES){
            return context.getFilesDir().getAbsolutePath();
        }
        return null;
    }


    public  String getExternalDir4Type(int type){
        if(type == KFConstants.ROOT){
            if(isDEBUG()){
                KFLog.i("");
            }
            return context.getExternalFilesDir(null).getParent();
        }else if(type == KFConstants.CACHE){
            return context.getExternalCacheDir().getAbsolutePath();
        }else if(type == KFConstants.FILES){
            return context.getExternalFilesDir(null).getAbsolutePath();
        }
        return null;
    }


    public  File createInSystemRoot(){
        if(!isSDCardEnableByEnvironment()){
            return null;
        }
        if(!isUsableSpace4Ext()){
            return null;
        }
        return Environment.getExternalStoragePublicDirectory(null).getParentFile();
    }

    public  boolean isUsableSpace4Int(){
        return context.getCacheDir().getParentFile().getUsableSpace()>1;
    }

    public  boolean isUsableSpace4Ext(){
        return Environment.getDataDirectory().getUsableSpace()>1;
    }


    public boolean isDEBUG() {
        return isDebug;
    }

    public void setDebug(boolean isDebug){
        this.isDebug = isDebug;
    }

    public File existDirs(int type, int where, String dirs){
        String root ;
        if(where==INT){
             root = getInternalDir4Type(type);
        }else if(where==EXT){
            root = getExternalDir4Type(type);
        }else {
            root = createInSystemRoot().getAbsolutePath();
        }
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
            return file.exists() ? file.getAbsoluteFile() : null;
        }
        return file.getAbsoluteFile();
    }
}
