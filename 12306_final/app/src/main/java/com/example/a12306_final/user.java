package com.example.a12306_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a12306_final.javabean._User;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class user extends AppCompatActivity {
    private TextView title, modify_password, modify_email;
    private String User_name;
    private Button exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Bmob.initialize(this,"22488e6103913f621999fda39c18cdec");
        Intent getObjectID=getIntent();
        final String ObjectID=getObjectID.getStringExtra("Object");
        final String password=getObjectID.getStringExtra("password");
        title=(TextView)findViewById(R.id.text_user_name);
        final _User userinfo = BmobUser.getCurrentUser(_User.class);
        if(userinfo != null){
            User_name = (String) BmobUser.getObjectByKey("realname");
        }
        else
        {
            Intent intent = new Intent(user.this,Login.class);
            startActivity(intent);
            finish();
        }
        title.setText(User_name);
        //修改密码
        modify_password=(TextView)findViewById(R.id.exchange_passward);
        modify_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPassword=new Intent(com.example.a12306_final.user.this, com.example.a12306_final.modify_password.class);
                toPassword.putExtra("Object",ObjectID);
                toPassword.putExtra("password",password);
                startActivity(toPassword);
            }
        });

        //修改邮箱
        modify_email=(TextView)findViewById(R.id.exchange_email);
        modify_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toEmail=new Intent(com.example.a12306_final.user.this, com.example.a12306_final.modify_email.class);
                toEmail.putExtra("Object",ObjectID);
                startActivity(toEmail);
            }
        });

        //退出登录
        exit=(Button)findViewById(R.id.exit_user);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userinfo.logOut();
                userinfo.getCurrentUser();
                Intent backtoLogin=new Intent(com.example.a12306_final.user.this,Login.class);
                startActivity(backtoLogin);
                finish();
            }
        });

    }

}
