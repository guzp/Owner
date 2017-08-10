package com.my.HttpUtil;

import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GZP on 2017/8/10.
 */

public class HttpConnectUtil {
    public static final Map<String, String> COOKIE = Collections.synchronizedMap(new HashMap<String, String>());
    public static final String USERAGENT = "Mozilla/5.0 (Windows NT 5.1; rv:25.0) Gecko/20100101 Firefox/25.0";
    public static int TIMEOUT = 60000;
    public static String TAG = "TAG";

    public static void initHeader(final Connection conn) {

        conn.header("Accept", "*/*")
                .header("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .userAgent(USERAGENT)
                .ignoreContentType(true)
                .followRedirects(false)
                .timeout(TIMEOUT);
    }

    /**
     *
     * @param url  完整接口
     * @param DataParam  Map 数据
     * @param InterfaceName String 接口名
     * @throws IOException
     * @throws InterruptedException
     */
    public static void Post(String url,Map DataParam,String InterfaceName) throws IOException, InterruptedException {
//        String url = getHost() + InterfaceName;
        Connection con = Jsoup.connect(url).postDataCharset("UTF-8").referrer(url).cookies(COOKIE).data(DataParam).method(Connection.Method.POST);
        initHeader(con);
        Connection.Response res = null;
        res = execute(con);
        if (res != null && res.statusCode() == 200) {
            Log.d(TAG, "上传结果:" + res.body());
            return;
        }

    }
    public static Connection.Response execute(Connection request) throws IOException, InterruptedException {
        Connection.Response call = null;
        for (int k = 0; k < 3; k++) {
            try {
                if (request == null) {
                    continue;
                }
                call = request.execute();
                break;
            } catch (InterruptedIOException ex) {
                Log.d(TAG, ex.getMessage());
                sleep(2);
                continue;
            } catch (IOException ex) {
                if (ex instanceof SocketTimeoutException) {
                    Log.d(TAG, ex.getMessage());
                    sleep(2);
                    continue;
                }
                if (ex instanceof java.net.ConnectException) {
                    Log.d(TAG, ex.getMessage());
                    sleep(2);
                    continue;
                }
                if (ex instanceof java.net.UnknownHostException) {
                    java.security.Security.setProperty("networkaddress.cache.ttl", "5");
                    //DnsCacheManipulator.setDnsCache("server01.pac.itzmx.com", "23.99.96.150");
                    //  0表示禁止缓存，-1表示永远有效
                    // System.out.println(InetAddress.getByName("server01.pac.itzmx.com").getHostAddress());
                    Log.d(TAG, ex.getMessage());
                    sleep(2);
                    continue;
                }
                if (ex instanceof java.net.SocketException) {
                    Log.d(TAG, ex.getMessage());
                    sleep(2);
                    continue;
                }
                if (k == 2) {
                    throw ex;
                }

            }
        }
        return call;
    }

    /**
     *
     * @param time 休眠时常 单位 ：秒
     */
    public static void sleep(int time) throws InterruptedException {
        for (int i = 0; i < time; i++) {
            Thread.sleep(time);
        }
    }



//    public static String getHost() {
//        String host = XmlBuildConfig.JSONSET.getString("host");
//        if (host == null) {
//            Util.loadConfig();
//            host = XmlBuildConfig.JSONSET.getString("host");
//            Log.d(TAG, "host 为null 从配置文件加载:" + host);
//        }
//        if (host==null){
//            host=XmlBuildConfig.Host;
//        }
//        return host;
//    }

}
