package com.my.File;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by GZP on 2017/7/8.
 */

public class FileEncoding {

    /**
     * 判断文件编码方式
     * @param filepath 文件路径
     * @return
     * @throws IOException
     */
    public static String getCharset(String filepath) throws IOException {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(filepath));
        int p = (bin.read() << 8) + bin.read();
        String code = null;
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        return code;
    }

    public static String getFileIncode(File file) {

        if (!file.exists()) {
            System.err.println("getFileIncode: file not exists!");
            return null;
        }

        byte[] buf = new byte[4096];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            // (1)
            UniversalDetector detector = new UniversalDetector(null);

            // (2)
            int nread;
            while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            // (3)
            detector.dataEnd();

            // (4)
            String encoding = detector.getDetectedCharset();
            if (encoding != null) {
                System.out.println("Detected encoding = " + encoding);
            } else {
                System.out.println("No encoding detected.");
            }

            // (5)
            detector.reset();
            fis.close();
            return encoding;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
