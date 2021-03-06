package top.keepempty.file;

import java.io.File;

/**
 * @auth: frey tse
 */
public class KeepFile {

    public File file;

    public int errorCode;

    public KeepFile(File file,int errorCode){
        this.file = file;
        this.errorCode = errorCode;
    }

    public KeepFile(File file) {
        this.file = file;
    }

    public boolean isSuccess(){
        return errorCode == 0 && file !=null;
    }

    public long getFileSize(){
        if(file.isFile()){
            return file.length();
        }
        return 0;
    }

    /****
     * 计算文件大小
     *
     * @param length
     * @return
     */
    public String ShowLongFileSzie(Long length) {
        if (length >= 1048576) {
            return (length / 1048576) + "MB";
        } else if (length >= 1024) {
            return (length / 1024) + "KB";
        } else if (length < 1024) {
            return length + "B";
        } else {
            return "0KB";
        }
    }

}
