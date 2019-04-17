package top.keepempty.file;

import android.app.Application;


/**
 * 说明：
 * 作者：fu.xie
 * 版本：
 * 创建日期：2019/4/17 10:10
 */
public class FileApplication extends Application {

   // private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        //refWatcher = LeakCanary.install(this);
    }

//    public static RefWatcher getRefWatcher(Context context) {
//        FileApplication application = (FileApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }




}
