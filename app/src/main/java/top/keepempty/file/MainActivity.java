package top.keepempty.file;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        File filesDir = getFilesDir();
//        printFilePath(filesDir,"getFileDir()");
//        File dir = getDir("Test", Context.MODE_PRIVATE);
//        printFilePath(dir,"getDir()");
//
//        File cacheDir = getCacheDir();
//        printFilePath(cacheDir,"getCacheDir()");
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            File dataDir = getDataDir();
//            printFilePath(dataDir,"getDataDir()");
//        }
//
//        File externalCacheDir = getExternalCacheDir();
//        printFilePath(externalCacheDir,"getExternalCacheDir()");
//
//        File externalFilesDir = getExternalFilesDir(null);
//        printFilePath(externalFilesDir,"getExternalFilesDir()");
//
//
//
//        File[] files;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            files = getExternalFilesDirs(Environment.MEDIA_MOUNTED);
//            for(File file:files){
//                Log.e("TEST",file.getAbsolutePath());
//            }
//        }
////
////        //File dirInFiles = KeepFileHelper.init(this).createDirInFiles(null);
////        //File test = KeepFileHelper.init(this).createDirInSysteRoot("");
////        //printFilePath(test,"=========");
////
////
        File rootDirectory = Environment.getRootDirectory().getParentFile();
        printFilePath(rootDirectory,"Environment.getRootDirectory()");

        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        printFilePath(externalStorageDirectory,"Environment.getExternalStorageDirectory()");

        File downloadCacheDirectory = Environment.getDownloadCacheDirectory();
        printFilePath(downloadCacheDirectory,"Environment.getDownloadCacheDirectory()");

        File dataDirectory = Environment.getDataDirectory();
        printFilePath(dataDirectory,"Environment.getDataDirectory()");

        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        printFilePath(externalStoragePublicDirectory,"Environment.getExternalStoragePublicDirectory()");

        File externalStoragePublicDirectoryPICTURES = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        printFilePath(externalStoragePublicDirectoryPICTURES,"Environment.getExternalStoragePublicDirectory(PICTURES)");
//
//        KeepFileHelper.init(this)
//                //.toInternal()
//                .showLog().createInAppPkg("demo/test");
//
//        KeepFileHelper.init(this).showLog().createInAppPkg("test.txt");

        KeepFileHelper.init(this).showLog().lastIsFile().createInAppPkg("/hehe2");
    }

    private void printFilePath(File filesDir,String name){
        try {
            Log.d("TEST",name+"-->AbsolutePath="+filesDir.getAbsolutePath());
            Log.d("TEST",name+"-->Name="+filesDir.getName());
            Log.d("TEST",name+"-->Canonical="+filesDir.getCanonicalPath());
            Log.d("TEST",name+"-->getPath="+filesDir.getPath());
            Log.d("TEST",name+"-->getParent="+filesDir.getParent());
            Log.d("TEST",name+"-->getFreeSpace="+filesDir.getFreeSpace());
            Log.d("TEST",name+"-->getTotalSpace="+filesDir.getTotalSpace());
            Log.d("TEST",name+"-->getUsableSpace="+filesDir.getUsableSpace());
            Log.d("TEST",name+"-->getParentFile="+filesDir.getParentFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void test(View view) {
        //KeepFileHelper.init(this).showLog().createInAppPkg("test/demo/hehe.txt");
        //KeepFileHelper.init(this).showLog().toInternal().createInAppPkg("test/demo/hehe1.txt");

//        File inAppPkg = KeepFileHelper.init(this)
//                .showLog()
//                .lastIsFile()
//                .createInAppPkg("test/demo/hehe2");
//
//        KeepFileLog.i("Result:",inAppPkg.getAbsolutePath());
//
//        KeepFileHelper.init(this).showLog().lastIsFile().replaceFile().createInAppPkg("test/demo/中文/12/1234.txt");
//        KeepFileHelper.init(this).showLog().lastIsFile().createInAppPkg("/hehe2");

        KeepFile qww1 = KeepFileHelper.init(this).createInPublic("Qww1/test.txt", Environment.DIRECTORY_PICTURES);

        //File qww2 = KeepFileHelper.init(this).createInPublic("Qww2", KeepFileConstants.ROOT);
        //KeepFileLog.i("Result:",qww1.getAbsolutePath());

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        RefWatcher refWatcher = FileApplication.getRefWatcher(this);
//        refWatcher.watch(this);
    }
}
