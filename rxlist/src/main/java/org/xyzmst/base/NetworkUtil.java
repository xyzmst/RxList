package org.xyzmst.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 13-8-23.
 */
public class NetworkUtil {
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }

        }
        return -1;
    }

    public static boolean isWifiConnecting(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            return wifi.isWifiEnabled() && getConnectedType(context) == ConnectivityManager.TYPE_WIFI;//返回true时表示存在，
        } catch (Exception e) {
            Log.e("error", e.toString());
            return false;
        }
    }

    public static boolean isNetConnecting(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("error", e.toString());
        }
        return false;
    }

    private static long parserNumber(String line) throws Exception {
        long ret = 0;
        String[] delim = line.split(" ");
        if (delim.length >= 1) {
            ret = Long.parseLong(delim[0]);
        }
        return ret;
    }

    public static long syncFetchReceivedBytes() {
        // TODO Auto-generated method stub
        ProcessBuilder cmd;
        long readBytes = 0;
        BufferedReader rd = null;
        try {
            String[] args = {"/system/bin/cat", "/proc/net/dev"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            rd = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line;
            int linecount = 0;
            while ((line = rd.readLine()) != null) {
                linecount++;
                if (line.contains("lan0") || line.contains("eth0")) {
                    String[] delim = line.split(":");
                    if (delim.length >= 2) {
                        readBytes = parserNumber(delim[1].trim());
                        break;
                    }
                }
            }
            rd.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return readBytes;
    }

    /**
     * 网络是否正常
     *
     * @param context Context
     * @return true 表示网络可用
     */
    public static int getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();

            if (type.equalsIgnoreCase("WIFI")) {
                return AVConstants.NETTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mobileInfo != null) {
                    switch (mobileInfo.getType()) {
                        case ConnectivityManager.TYPE_MOBILE:// 手机网络
                            switch (mobileInfo.getSubtype()) {
                                case TelephonyManager.NETWORK_TYPE_UMTS:
                                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                case TelephonyManager.NETWORK_TYPE_HSDPA:
                                case TelephonyManager.NETWORK_TYPE_HSUPA:
                                case TelephonyManager.NETWORK_TYPE_HSPA:
                                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                                case TelephonyManager.NETWORK_TYPE_EHRPD:
                                case TelephonyManager.NETWORK_TYPE_HSPAP:
                                    return AVConstants.NETTYPE_3G;
                                case TelephonyManager.NETWORK_TYPE_CDMA:
                                case TelephonyManager.NETWORK_TYPE_GPRS:
                                case TelephonyManager.NETWORK_TYPE_EDGE:
                                case TelephonyManager.NETWORK_TYPE_1xRTT:
                                case TelephonyManager.NETWORK_TYPE_IDEN:
                                    return AVConstants.NETTYPE_2G;
                                case TelephonyManager.NETWORK_TYPE_LTE:
                                    return AVConstants.NETTYPE_4G;
                                default:
                                    return AVConstants.NETTYPE_NONE;
                            }
                    }
                }
            }
        }

        return AVConstants.NETTYPE_NONE;
    }


    /*
     * 获取网络类型
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取网络文件源码大小，不含图片等信息大小
     * 通过代理访问，如果第一次获取不到数据，则连接第二次
     *
     * @param destUrl 网络html地址  ps:http://www.baidu.com.cn
     * @return
     * @author admin, 2015年4月16日.
     */
    public static long getWebFileCount(String destUrl) throws IOException {
        URL url;
        url = new URL(destUrl);
        HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
        urlcon.setRequestProperty("User-Agent", " Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36");
        urlcon.setConnectTimeout(15000);
        urlcon.setReadTimeout(15000);
        //根据响应获取文件大小
        return (long) urlcon.getContentLength();
    }


    /**
     * 是否连接的手机网络
     *
     * @param context
     * @return
     */
    public static boolean isConectMobile(Context context) {
        boolean isConnect = false;
        if (NetworkUtil.getNetWorkType(context) == AVConstants.NETTYPE_2G || NetworkUtil.getNetWorkType(context) == AVConstants.NETTYPE_3G || NetworkUtil.getNetWorkType(context) == AVConstants.NETTYPE_4G) {
            isConnect = true;
        }
        return isConnect;
    }
}
