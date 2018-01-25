package com.my.File;


import java.io.File;
import java.nio.charset.Charset;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

public class FileCharsetDetector {


    public void fi(){
        String filepath="C:\\Users\\gzp\\Desktop\\任务记录.txt";
        File file=new File(filepath);
        if (!file.exists()){
            return;
        }
        String con=getFileEncode(file);
        System.out.println(con);

    }

    /**
     * 利用第三方开源包cpdetector获取文件编码格式.
     *
     * @param file
     * @return
     */
    public static String getFileEncode(File file) {
        /**
         * <pre>
         * 1、cpDetector内置了一些常用的探测实现类,这些探测实现类的实例可以通过add方法加进来,
         * 如:ParsingDetector、 JChardetFacade、ASCIIDetector、UnicodeDetector.
         * 2、detector按照“谁最先返回非空的探测结果,就以该结果为准”的原则.
         * 3、cpDetector是基于统计学原理的,不保证完全正确.
         * </pre>
         */
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();

        detector.add(new ParsingDetector(false));
        detector.add(UnicodeDetector.getInstance());
        detector.add(JChardetFacade.getInstance());//内部引用了 chardet.jar的类
        detector.add(ASCIIDetector.getInstance());

        Charset charset = null;
        try {
            charset = detector.detectCodepage(file.toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //默认为GBK
        String charsetName = "GBK";
        if (charset != null) {
            if (charset.name().equals("US-ASCII")) {
                charsetName = "ISO_8859_1";
            } else {
                charsetName = charset.name();
            }
        }
        return charsetName;
    }
}
