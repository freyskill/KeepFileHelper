package top.keepempty.file;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File filesDir = getFilesDir();
        printFilePath(filesDir,"getFileDir()");
        File dir = getDir("Test", Context.MODE_PRIVATE);
        printFilePath(dir,"getDir()");

        File cacheDir = getCacheDir();
        printFilePath(cacheDir,"getCacheDir()");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            File dataDir = getDataDir();
            printFilePath(dataDir,"getDataDir()");
        }

        File externalCacheDir = getExternalCacheDir();
        printFilePath(externalCacheDir,"getExternalCacheDir()");

        File externalFilesDir = getExternalFilesDir(null);
        printFilePath(externalFilesDir,"getExternalFilesDir()");

        File[] files;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            files = getExternalFilesDirs(Environment.MEDIA_MOUNTED);
            for(File file:files){
                Log.e("TEST",file.getAbsolutePath());
            }
        }

        //File dirInFiles = KeepFileManager.init(this).createDirInFiles(null);

        File rootDirectory = Environment.getRootDirectory();
        printFilePath(rootDirectory,"Environment.getRootDirectory()");

        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        printFilePath(externalStorageDirectory,"Environment.getExternalStorageDirectory()");

        File downloadCacheDirectory = Environment.getDownloadCacheDirectory();
        printFilePath(downloadCacheDirectory,"Environment.getDownloadCacheDirectory()");

        File dataDirectory = Environment.getDataDirectory();
        printFilePath(dataDirectory,"Environment.getDataDirectory()");

        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        printFilePath(externalStoragePublicDirectory,"Environment.getExternalStoragePublicDirectory()");


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
}
