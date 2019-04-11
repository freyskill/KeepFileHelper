package top.keepempty.file.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import top.keepempty.file.KFConstants;

/**
 * @auth: frey tse
 */
public class KFUtils {

    public static boolean DEBUG = false;

    public static boolean isSDCardEnableByEnvironment(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static String getInternalDir4Type(Context context,int type){
        if(type == KFConstants.ROOT){
            if(KFUtils.isDEBUG()){

            }
            return context.getFilesDir().getParent();
        }else if(type == KFConstants.CACHE){
            return context.getCacheDir().getAbsolutePath();
        }else if(type == KFConstants.FILES){
            return context.getFilesDir().getAbsolutePath();
        }
        return null;// 传入类型
    }


    public static boolean isUsableSpace4Int(Context context){
        return context.getFilesDir().getParentFile().getUsableSpace()>1;
    }


    public static String getExternalDir4Type(Context context,int type){
        return context.getExternalFilesDir(null).getParent();
    }

    public static boolean isDEBUG() {
        return DEBUG;
    }

    public static void setDebug(boolean isDebug){
        DEBUG = isDebug;
    }
}
