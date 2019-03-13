package com.example.a12306_final;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.net.URL;

public class Activity_server extends AppCompatActivity {
    private TextView diancan,yueche;
    TextView menu_deal,menu_sever,menu_query,menu_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_server);
        diancan=(TextView)findViewById(R.id.sever_dingcan);
        yueche=(TextView)findViewById(R.id.sever_yueche);

        diancan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri ="http://www.meituan.com";
                Intent intent=new Intent(Activity_server.this, com.example.a12306_final.WebView.class);
                intent.putExtra("uri",uri);
                startActivity(intent);
            }
        });
        yueche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri ="http://www.xiaojukeji.com";
                Intent intent=new Intent(Activity_server.this, com.example.a12306_final.WebView.class);
                intent.putExtra("uri",uri);
                startActivity(intent);
            }
        });
        //目录监听
        menu_deal=(TextView)findViewById(R.id.menu_txt_deal);
        menu_sever=(TextView)findViewById(R.id.menu_txt_sever);
        menu_query=(TextView)findViewById(R.id.menu_txt_query);
        menu_user=(TextView)findViewById(R.id.menu_txt_user);
        menu_sever.setSelected(true);
        menu_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!menu_deal.isSelected()){
                    menu_deal.setSelected(true);
                    menu_sever.setSelected(false);
                    menu_user.setSelected(false);
                    menu_query.setSelected(false);
                }
                Intent toMain=new Intent(Activity_server.this, MainActivity.class);
                startActivity(toMain);
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
                Intent toOrder=new Intent(Activity_server.this, Activity_Order.class);
                startActivity(toOrder);
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
                Intent my12306_activity = new Intent(Activity_server.this, My12306.class);
                startActivity(my12306_activity);
                finish();
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
