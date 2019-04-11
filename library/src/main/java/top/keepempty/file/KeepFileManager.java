package top.keepempty.file;

import android.content.Context;

/**
 * 1.创建本地文件目录
 * 2.获取指定目录文件
 * @auth: frey tse
 */
public class KeepFileManager {

    private Context mContext;

    private static KeepFileManager keepFileManager;

    private KeepFileManager(Context context){
        this.mContext = context;
    }

    public static KeepFileManager init(Context context){
        if(keepFileManager==null){
            return new KeepFileManager(context.getApplicationContext());
        }
        return keepFileManager;
    }

    /**
     * 设置保存到内部存储
     * @return
     */
    public KeepFileManager toInternal(){
        return this;
    }



}
