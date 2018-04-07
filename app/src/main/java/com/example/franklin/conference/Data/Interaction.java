package com.example.franklin.conference.Data;

//此类包含向服务器提交的各种数据，包括登陆，注册等等。。。

import org.json.JSONException;
import org.json.JSONObject;

public class Interaction {

    public  JSONObject LoginData(String username, String password) {

        JSONObject js=new JSONObject();
        try {
            js.put("username", username);
            js.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js;
    }
    public  JSONObject RegisterData(String username,String auth_code,String phone,String password){
        JSONObject js=new JSONObject();
        try {
            js.put("username", username);
            js.put("code", auth_code);
            js.put("mobile", phone);
            js.put("password", password);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return js;
    }

    public  JSONObject ConData(String name,String content,String place,String type,boolean is_open,String time) {

        JSONObject js=new JSONObject();
        try {
            js.put("name", name);
            js.put("conference_content",content);
            js.put("conference_place",place);
            js.put("type",type);
            js.put("is_public",is_open);
            js.put("conference_time",time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js;
    }
}

