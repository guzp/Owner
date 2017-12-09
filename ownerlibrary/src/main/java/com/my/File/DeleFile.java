package com.my.File;

import java.io.File;

/**
 * Created by GZP on 2017/12/9.
 */

public class DeleFile {
    //删除文件夹或文件
    public void DeleteFileOrDir(File file) {
        if (file.isDirectory()) {
            File[] childfiles = file.listFiles();
            if (childfiles != null && childfiles.length > 0) {
                for (int i = 0; i < childfiles.length; i++) {
                    DeleteFileOrDir(childfiles[i]);
                }
            }
            //如果不删除文件夹 可注销
            file.delete();
        } else if (file.exists()) {
            file.delete();
        }
    }
}
