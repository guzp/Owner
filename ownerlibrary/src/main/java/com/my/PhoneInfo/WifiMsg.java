package com.my.PhoneInfo;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GZP on 2017/7/21.
 *
 * 获取wifi信息
 *
 */

public class WifiMsg {

    public static Map GetAllWifiInfo(Context mcontext) throws InvocationTargetException, IllegalAccessException {
        Map map=new HashMap();
        WifiManager manager = (WifiManager) mcontext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        Class<?> wifiinfoclass = info.getClass();
        StringBuilder builder = new StringBuilder();
        Method meths[] = wifiinfoclass.getMethods();
        for (int i = 0; i < meths.length; i++) {
            meths[i].getParameterTypes();
            if (meths[i].getParameterTypes().length == 0) {
                builder.append("" + meths[i].getName() + ": " + meths[i].invoke(info, new String[]{}) + "\n");
                map.put(meths[i].getName(),meths[i].invoke(info, new String[]{}));
            }
        }
        int i = info.getIpAddress();
        String ip = (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
        builder.append("getIpAddress:" + ip).toString();
        map.put("AllWifiInfo",builder.toString());
        return map;
    }

}
