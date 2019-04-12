package top.keepempty.file;


import android.util.Log;

public class KFLog {

    private static final boolean ADB = true;

    private static final String TAG = KFLog.class.getSimpleName();

    private static final int LOG_DEGREE = Log.VERBOSE;

    public static void v(String... msg) {
        if (ADB && LOG_DEGREE <= Log.VERBOSE) {
            String msgStr = combineLogMsg(msg);
            Log.v(TAG, msgStr);
        }
    }


    public static void d(String... msg) {
        if (ADB && LOG_DEGREE <= Log.DEBUG) {
            String msgStr = combineLogMsg(msg);
            Log.d(TAG, msgStr);
        }
    }


    public static void i(String... msg) {
        if (ADB && LOG_DEGREE <= Log.INFO) {
            String msgStr = combineLogMsg(msg);
            Log.i(TAG, msgStr);
        }
    }


    public static void w(String... msg) {
        if (ADB && LOG_DEGREE <= Log.WARN) {
            String msgStr = combineLogMsg(msg);
            Log.w(TAG, msgStr);
        }
    }


    public static void e(String... msg) {
        if (ADB && LOG_DEGREE <= Log.ERROR) {
            String msgStr = combineLogMsg(msg);
            Log.e(TAG, msgStr);
        }
    }

    public static void e(Throwable tr, String... msg) {
        if (ADB && LOG_DEGREE <= Log.ERROR) {
            String msgStr = combineLogMsg(msg);
            Log.e(TAG, msgStr, tr);
        }
    }

    private static String combineLogMsg(String... msg) {
        if (null == msg){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : msg) {
            sb.append(s);
        }
        return sb.toString();
    }

}
