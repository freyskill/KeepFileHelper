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
}
