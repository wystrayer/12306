package com.example.a12306_final;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12306_final.javabean._User;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class My12306_Logined extends AppCompatActivity {

    private String User_name;
    private String object;
    private TextView textview_name;
    private TextView ceshi;
    private TextView menu_deal,menu_sever,menu_query,menu_user;
    private TextView phconfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_my12306_logined);
        Bmob.initialize(this,"22488e6103913f621999fda39c18cdec");
        _User userinfo = BmobUser.getCurrentUser(_User.class);
        if(userinfo != null){

            User_name = (String) BmobUser.getObjectByKey("realname");
            object = (String) BmobUser.getObjectByKey("objectId");
        }
        else
        {
            Intent intent = new Intent(My12306_Logined.this,Login.class);
            //startActivityForResult(intent,4);
            startActivity(intent);
            finish();
        }

        phconfig = (TextView)findViewById(R.id.my12306_phconfig);
        BmobQuery<_User> query = new BmobQuery<_User>();
        query.addWhereEqualTo("objectId", object);
        query.findObjects(new FindListener<_User>() {
            @Override
            public void done(List<_User> object,BmobException e) {
                if(e==null){
                    for (_User user : object) {
                        //获得playerName的信息
                        update(user.getMobilePhoneNumberVerified());
                    }
                }else{

                }
            }
        });
      //测试使用
        ceshi = (TextView)findViewById(R.id.my12306_dingcan);
        ceshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _User newUser = new _User();
                newUser.setMobilePhoneNumberVerified(false);
                newUser.update(object,new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(getBaseContext(),"更新用户信息成功",Toast.LENGTH_SHORT).show();
                        }else{
                        }
                    }
                });
            }
        });

        //用户资料
        textview_name=(TextView)findViewById(R.id.my12306_logined_usrname);
        textview_name.setText(User_name);
        textview_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toUser= new Intent(My12306_Logined.this, com.example.a12306_final.user.class);
                startActivity(toUser);
                finish();
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
                Intent to_main=new Intent(My12306_Logined.this, MainActivity.class);
                startActivity(to_main);
                finish();
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
                Intent toquery=new Intent(My12306_Logined.this,Activity_Order.class);
                startActivity(toquery);
                finish();
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
                Intent toSever=new Intent(My12306_Logined.this, Activity_server.class);
                startActivity(toSever);
                finish();
            }
        });

    }

    private void update(final Boolean confirm){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(),confirm.toString(),Toast.LENGTH_SHORT).show();
                if(confirm) {
                    phconfig.setText("手机验证（已认证）");
                }
                else{
                    phconfig.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(My12306_Logined.this, phone_config.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
    /*
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case 4:
                if(resultCode == RESULT_OK){
                    String returndata = data.getStringExtra("data_return");
                    if(returndata.equals("true")){
                        phconfig.setText("手机验证（已认证）");
                    }
                }
        }
    }
    */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("确认退出吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SysApplication.getInstance().exit();
                    }
                }).show();
    }


}
