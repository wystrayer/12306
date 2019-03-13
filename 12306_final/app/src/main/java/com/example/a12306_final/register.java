package com.example.a12306_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a12306_final.javabean._User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class register extends AppCompatActivity {
    private EditText user_name;
    private EditText user_password;
    private EditText user_repassword;
    private EditText user_realname;
    private EditText user_ID;
    private EditText user_phone;
    private EditText user_Emailaddress;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.register);
        Bmob.initialize(this,"22488e6103913f621999fda39c18cdec");
        user_name = (EditText) findViewById(R.id.input_userName);
        user_password = (EditText) findViewById(R.id.input_userPassward);
        user_repassword = (EditText) findViewById(R.id.input_userRePassward);
        user_realname = (EditText) findViewById(R.id.input_RealName);
        user_ID = (EditText) findViewById(R.id.input_userID);
        user_phone = (EditText) findViewById(R.id.input_userPhone);
        user_Emailaddress = (EditText) findViewById(R.id.input_userEmailAddress);
        btn_register = (Button) findViewById(R.id.register_button);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = user_password.getText().toString();
               if(password.equals(user_repassword.getText().toString()))
                {
                    _User user =new _User();
                    user.setUsername(user_name.getText().toString());
                    user.setPassword(user_password.getText().toString());
                    user.setEmail(user_Emailaddress.getText().toString());
                    user.setMobilePhoneNumber(user_phone.getText().toString());
                    user.setUserId(user_ID.getText().toString());
                    user.setRealname(user_realname.getText().toString());
                    user.setPassword_config(user_repassword.getText().toString());
                    user.setMobilePhoneNumberVerified(false);
                    user.signUp(new SaveListener<_User>() {
                        @Override
                        public void done(_User s, BmobException e){
                            if(e == null){
                                Toast.makeText(getBaseContext(),"注册成功：" + s.toString(),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(register.this,Login.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                   Toast.makeText(getBaseContext(),"请重新输入密码",Toast.LENGTH_SHORT).show();
               }
            }
        });
    }
}