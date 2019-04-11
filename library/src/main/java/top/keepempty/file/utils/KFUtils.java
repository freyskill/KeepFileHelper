package top.keepempty.file.utils;

import android.os.Environment;

/**
 * @auth: frey tse
 */
public class KFUtils {


    public static boolean isSDCardEnableByEnvironment(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }



}
