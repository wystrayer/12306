package com.example.a12306_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class modify_email extends AppCompatActivity {
    private String User_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_email);
        Intent getObjectID=getIntent();
        final String ObjectID=getObjectID.getStringExtra("Object");
    }
}
