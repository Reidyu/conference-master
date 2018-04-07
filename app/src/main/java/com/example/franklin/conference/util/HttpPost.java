package com.example.franklin.conference.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.franklin.conference.App.MyApplication;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HttpPost {

    public static String JsonPost(final String path,  final JSONObject json) {
        String result="";
        BufferedReader in = null;
        OutputStream os = null;

        try {
            URL url = new URL(path);
// 然后我们使用httpPost的方式把lientKey封装成Json数据的形式传递给服务器
// 在这里呢我们要封装的时这样的数据
// 我们把JSON数据转换成String类型使用输出流向服务器写
            String content = String.valueOf(json);
// 现在呢我们已经封装好了数据,接着呢我们要把封装好的数据传递过去
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(8000);
// 设置允许输出
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
// 设置User-Agent: Fiddler
            //conn.setRequestProperty("ser-Agent", "Fiddler");
// 设置contentType
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            //将token放入头部
           SharedPreferences pref =  MyApplication.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
           String token = pref.getString("JWT","");
            conn.setRequestProperty("Authorization", "JWT "+token);

            os = conn.getOutputStream();
            os.write(content.getBytes());
            os.flush();
//获取服务器的返回码getResponseCode, 200,201表示正常，400表示异常
            int statusCode = conn.getResponseCode();
// 定义BufferedReader输入流来读取URL的响应
            if (statusCode/100 == 2) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }else if (statusCode/100 != 2){
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }


            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

        } catch (SocketTimeoutException e) {
// Log.i("错误", "连接时间超时");
            e.printStackTrace();
            return "1";
        } catch (MalformedURLException e) {
// Log.i("错误", "jdkfa");
            e.printStackTrace();
            return "2";
        } catch (ProtocolException e) {
// Log.i("错误", "jdkfa");
            e.printStackTrace();
            return "3";
        } catch (IOException e) {
// Log.i("错误", "jdkfa");
            e.printStackTrace();
            return "4";
        }// 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String ConferencePost(final String path,  final JSONObject json) {
        String result="";
        BufferedReader in = null;
        OutputStream os = null;

        try {
            URL url = new URL(path);
// 然后我们使用httpPost的方式把lientKey封装成Json数据的形式传递给服务器
// 在这里呢我们要封装的时这样的数据
// 我们把JSON数据转换成String类型使用输出流向服务器写
            String content = String.valueOf(json);
// 现在呢我们已经封装好了数据,接着呢我们要把封装好的数据传递过去
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(8000);
            conn.setReadTimeout(8000);
// 设置允许输出
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
// 设置User-Agent: Fiddler
            //conn.setRequestProperty("ser-Agent", "Fiddler");
// 设置contentType
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            //将token放入头部
            SharedPreferences pref =  MyApplication.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            String token = pref.getString("JWT","");
            conn.setRequestProperty("Authorization", "JWT "+token);

            os = conn.getOutputStream();
            os.write(content.getBytes());
            os.flush();
//获取服务器的返回码getResponseCode, 200,201表示正常，400表示异常
            int statusCode = conn.getResponseCode();
// 定义BufferedReader输入流来读取URL的响应
            if (statusCode/100 == 2) {
                result ="会议已成功创建！";
                //in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }else if (statusCode/100 != 2){
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                return result;
            }




        } catch (SocketTimeoutException e) {
// Log.i("错误", "连接时间超时");
            e.printStackTrace();
            return "1";
        } catch (MalformedURLException e) {
// Log.i("错误", "jdkfa");
            e.printStackTrace();
            return "2";
        } catch (ProtocolException e) {
// Log.i("错误", "jdkfa");
            e.printStackTrace();
            return "3";
        } catch (IOException e) {
// Log.i("错误", "jdkfa");
            e.printStackTrace();
            return "4";
        }// 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}
