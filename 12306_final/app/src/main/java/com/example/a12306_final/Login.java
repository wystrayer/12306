package com.example.a12306_final;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12306_final.javabean._User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Login extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberPass;
    private CheckBox autologin;
    private TextView menu_deal,menu_sever,menu_query,menu_user;
    private Button btn_register;
    private Button btn_login;
    private String account;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.login);
        Bmob.initialize(this,"22488e6103913f621999fda39c18cdec");

            //Toast.makeText(getBaseContext(),sflag,Toast.LENGTH_LONG).show();
        pref= getSharedPreferences("date_info",MODE_PRIVATE);
        accountEdit=(EditText)findViewById(R.id.accountEdit);
        passwordEdit=(EditText)findViewById(R.id.passwordEidt);
        rememberPass=(CheckBox)findViewById(R.id.Box_pass);
        autologin = (CheckBox) findViewById(R.id.Box_login);
        boolean isRemember= pref.getBoolean("remember_password",false);
        boolean isAutologin= pref.getBoolean("autologin",false);
        if(isRemember){
            //将账号和密码设置到文本框
            account=pref.getString("account","");
            password=pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
            if(isAutologin){
                autologin.setChecked(true);
            }
        }else {
            account=pref.getString("account","");
            accountEdit.setText(account);
        }
        btn_register=(Button)findViewById(R.id.Register_button);
        btn_login=(Button)findViewById(R.id.Login_button);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_to_register=new Intent(Login.this, register.class);
                startActivity(login_to_register);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = accountEdit.getText().toString();
                password = passwordEdit.getText().toString();
                if (!account.equals("") && !password.equals("")) {
                    editor = pref.edit();
                    if (rememberPass.isChecked()) {
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password", password);
                        if(autologin.isChecked()) {
                            editor.putBoolean("autologin",true);
                        }
                        else{
                            editor.putBoolean("autologin",false);
                        }
                    } else {
                        editor.clear();
                        editor.putString("account", account);
                    }
                    editor.apply();
                }
                _User user = new _User();
                user.setUsername(account);
                user.setPassword(password);
                user.login(new SaveListener<_User>() {
                    @Override
                    public void done(_User bmobUser, BmobException e) {
                        if (e == null) {
                            Toast.makeText(getBaseContext(), "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this,My12306_Logined.class);
                            startActivity(intent);
                            finish();
                            //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                            //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                        }
                        else {
                            Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //目录监听
        menu_deal=(TextView)findViewById(R.id.menu_txt_deal);
        menu_sever=(TextView)findViewById(R.id.menu_txt_sever);
        menu_query=(TextView)findViewById(R.id.menu_txt_query);
        menu_user=(TextView)findViewById(R.id.menu_txt_user);
        menu_user.setSelected(true);
        menu_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!menu_deal.isSelected()){
                    menu_deal.setSelected(true);
                    menu_sever.setSelected(false);
                    menu_user.setSelected(false);
                    menu_query.setSelected(false);
                }
                Intent to_main=new Intent(Login.this, MainActivity.class);
                startActivity(to_main);
            }
        });
        menu_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!menu_query.isSelected()){
                    menu_deal.setSelected(false);
                    menu_sever.setSelected(false);
                    menu_user.setSelected(false);
                    menu_query.setSelected(true);
                }
            }
        });
        menu_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!menu_user.isSelected()){
                    menu_deal.setSelected(false);
                    menu_sever.setSelected(false);
                    menu_user.setSelected(true);
                    menu_query.setSelected(false);
                }
            }
        });
        menu_sever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!menu_sever.isSelected()){
                    menu_deal.setSelected(false);
                    menu_sever.setSelected(true);
                    menu_user.setSelected(false);
                    menu_query.setSelected(false);
                }
            }
        });
    }

}
