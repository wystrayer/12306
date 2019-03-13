package com.example.a12306_final;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Welcome extends AppCompatActivity {

    private final long SPLASH_LENGTH = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler.sendEmptyMessageDelayed(0,SPLASH_LENGTH);
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            gethome();
            super.handleMessage(msg);
        }
    };
    private void gethome(){
        Intent intent=new Intent(Welcome.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
