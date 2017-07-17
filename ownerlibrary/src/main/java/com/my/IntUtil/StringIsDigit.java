package com.my.IntUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GZP on 2017/7/9.
 */

public class StringIsDigit {
    // 判断一个字符串是否都为数字
    public boolean isDigit(Object object) {
        if (object==null){
            return false;
        }
        String strNum=String.valueOf(object);
        if (strNum==null){
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) strNum);
        return matcher.matches();
    }

    //截取数字
    public String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    // 截取非数字
    public String splitNotNumber(String content) {
        Pattern pattern = Pattern.compile("\\D+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    // 判断一个字符串是否含有数字
    public boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }
}
