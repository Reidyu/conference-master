package com.example.franklin.conference.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.franklin.conference.Data.Interaction;
import com.example.franklin.conference.R;
import com.example.franklin.conference.util.HttpPost;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Franklin on 2018/3/30.
 */

public class Register extends AppCompatActivity {

    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private EditText mPwdCheck;                       //密码编辑
    private EditText mPhone;                          //手机编辑
    private EditText mAuthcode;                       //验证码

    String result1 ="";
    String result2 ="";
    private Button mSureButton;                       //确定按钮
    private Button mCancelButton;                     //取消按钮
    private Button mcode;                             //获取验证码
    //private UserDataManager mUserDataManager;         //用户数据管理类
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAccount = (EditText) findViewById(R.id.register_username);
        mPwd = (EditText) findViewById(R.id.register_password);
        mPwdCheck = (EditText) findViewById(R.id.register_password_check);
        mPhone = (EditText) findViewById(R.id.register_phone);
        mAuthcode = (EditText) findViewById(R.id.auth_code);

        mSureButton = (Button) findViewById(R.id.register_btn_sure);
        mCancelButton = (Button) findViewById(R.id.register_btn_cancel);
        mcode = (Button) findViewById(R.id.register_btn_auth);

        mSureButton.setOnClickListener(m_register_Listener);      //注册界面两个按钮的监听事件
        mCancelButton.setOnClickListener(m_register_Listener);
        mcode.setOnClickListener(m_register_Listener);

//        if (mUserDataManager == null) {
//            mUserDataManager = new UserDataManager(this);
//            mUserDataManager.openDataBase();                              //建立本地数据库
//        }

    }
    View.OnClickListener m_register_Listener = new View.OnClickListener() {    //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.register_btn_sure:                       //确认按钮的监听事件
                    register_check();
                    break;
                case R.id.register_btn_cancel:                     //取消按钮的监听事件,由注册界面返回登录界面
                    Intent intent_Register_to_Login = new Intent(Register.this,Login.class) ;    //切换User Activity至Login Activity
                    startActivity(intent_Register_to_Login);
                    finish();
                    break;
                case R.id.register_btn_auth:
                    register_code();
                    break;
            }
        }
    };
    public void register_check() {                                //确认按钮的监听事件
        if (isUserNameAndPwdValid()) {
            final String userName = mAccount.getText().toString().trim();
            final String userPwd = mPwd.getText().toString().trim();
            final String userPwdCheck = mPwdCheck.getText().toString().trim();
            final String phone = mPhone.getText().toString().trim();
            final String auth_code = mAuthcode.getText().toString().trim();

            if(userPwd.equals(userPwdCheck)==false){     //两次密码输入不一样
                Toast.makeText(this, getString(R.string.pwd_not_the_same),Toast.LENGTH_SHORT).show();
                return ;
            }

            //生成Json格式的数据
            Interaction interaction = new Interaction();
            final JSONObject jsonData = interaction.RegisterData(userName,auth_code,phone, userPwd);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    result1 = HttpPost.JsonPost("http://39.108.186.78/users/", jsonData);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result1.indexOf(phone) != -1) {      //服务器返回的数据包含手机号
                                Toast.makeText(Register.this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                                Intent intent_Register_to_Login = new Intent(Register.this, Login.class);    //切换User Activity至Login Activity
                                startActivity(intent_Register_to_Login);
                                finish();
                            }

                            else if (result1.indexOf("mobile") != -1)
                                Toast.makeText(Register.this, "手机号已被注册！", Toast.LENGTH_SHORT).show();
                            else if (result1.indexOf("code") != -1)
                                Toast.makeText(Register.this, "验证码错误或已失效！", Toast.LENGTH_SHORT).show();
                            else if (result1.indexOf("username") != -1)
                                Toast.makeText(Register.this, "用户名已存在！", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(Register.this, result1, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();

            }
        }
    public void register_code(){
        final String phone = mPhone.getText().toString().trim();
        final JSONObject js=new JSONObject();
        try{
            js.put("mobile",phone);
        }catch (JSONException e){
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                result2 = HttpPost.JsonPost("http://39.108.186.78/codes/", js);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(result2.indexOf(phone) != -1){
                            Toast.makeText(Register.this, "验证码已发送", Toast.LENGTH_LONG).show();
                        }
//                        if (result2.indexOf("mobile") != -1){
                        else {
                            Toast.makeText(Register.this, result2, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).start();
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
        }else if(mPwdCheck.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(mPhone.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.phone_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(mAuthcode.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.authcode_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
         return true;
    }
}
