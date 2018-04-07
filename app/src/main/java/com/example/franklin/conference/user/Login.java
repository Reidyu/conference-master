package com.example.franklin.conference.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franklin.conference.Data.Interaction;
import com.example.franklin.conference.MainActivity;
import com.example.franklin.conference.R;
import com.example.franklin.conference.util.HttpPost;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

/**
 * Created by Franklin on 2018/3/30.
 */

public class Login extends AppCompatActivity {

    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private Button mRegisterButton;                   //注册按钮
    private Button mLoginButton;                      //登录按钮

    private CheckBox mRememberCheck;
    private SharedPreferences login_sp;

//    private UserDataManager mUserDataManager;         //用户数据管理类

    String result="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //通过id找到相应的控件
        mAccount = (EditText) findViewById(R.id.login_edit_account);
        mPwd = (EditText) findViewById(R.id.login_edit_pwd);
        mRegisterButton = (Button) findViewById(R.id.login_btn_register);
        mLoginButton = (Button) findViewById(R.id.login_btn_login);



        mRememberCheck = (CheckBox) findViewById(R.id.Login_Remember);

        login_sp = getSharedPreferences("userInfo", 0);
        String name=login_sp.getString("USER_NAME", "");
        String pwd =login_sp.getString("PASSWORD", "");
        boolean choseRemember =login_sp.getBoolean("mRememberCheck", false);
       // boolean choseAutoLogin =login_sp.getBoolean("mAutologinCheck", false);
        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if(choseRemember){
            mAccount.setText(name);
            mPwd.setText(pwd);
            mRememberCheck.setChecked(true);
        }

        mRegisterButton.setOnClickListener(mListener);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件
        mLoginButton.setOnClickListener(mListener);

        ImageView image = (ImageView) findViewById(R.id.logo);             //使用ImageView显示logo
        image.setImageResource(R.drawable.logo);

//        if (mUserDataManager == null) {
//            mUserDataManager = new UserDataManager(this);
//            mUserDataManager.openDataBase();                              //建立本地数据库
//        }
    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_register:                            //登录界面的注册按钮
                    Intent intent_Login_to_Register = new Intent(Login.this,Register.class) ;    //切换Login Activity至User Activity
                    startActivity(intent_Login_to_Register);
                    finish();
                    break;
                case R.id.login_btn_login:                              //登录界面的登录按钮
                    login();
                    break;
            }
        }
    };


    public void login() {//登录按钮监听事件
        if (isUserNameAndPwdValid()) {
            final String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息
            final String userPwd = mPwd.getText().toString().trim();
            final SharedPreferences.Editor editor = login_sp.edit();
            //生成Json格式的数据
            Interaction interaction = new Interaction();
            final JSONObject jsonData = interaction.LoginData(userName, userPwd);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    result = HttpPost.JsonPost("http://39.108.186.78/api-login/", jsonData);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.indexOf("token") != -1) {          //服务器返回结果包含“token”字符串
                                //保存用户名和密码
                                editor.putString("USER_NAME", userName);
                                editor.putString("PASSWORD", userPwd);
                                //保存服务器返回的JWT，用于后续的身份验证
                                String temptoken = StringUtils.substringBetween(result,":","}");
                                String token = StringUtils.substringBetween(temptoken,"\"","\"");
                                editor.putString("JWT",token);
                                //是否记住密码
                                if (mRememberCheck.isChecked()) {
                                    editor.putBoolean("mRememberCheck", true);
                                } else {
                                    editor.putBoolean("mRememberCheck", false);
                                }
                                editor.apply();

                                Intent intent = new Intent(Login.this, MainActivity.class);    //切换Login Activity至MainActivity
                                startActivity(intent);
                                finish();
                                Toast.makeText(Login.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();//登录成功提示
                            } else
                                Toast.makeText(Login.this,getString(R.string.login_fail), Toast.LENGTH_SHORT).show();  //登录失败提示
                        }
                    });
                }
            }).start();
        }
    }


    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
