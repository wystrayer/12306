package com.example.a12306_final.assets;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a12306_final.javabean.MonthDateView;
import com.example.a12306_final.R;
import com.example.a12306_final.SysApplication;

import java.util.ArrayList;
import java.util.List;

public class Activity_Calendar extends FragmentActivity {

    private ImageView iv_left;
    private ImageView iv_right;
    private TextView tv_date;
    private TextView tv_week;
    private TextView tv_today;
    private MonthDateView monthDateView;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(12);
        list.add(15);
        list.add(16);

        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.calendar);
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        monthDateView = (MonthDateView) findViewById(R.id.monthDateView);
        tv_date = (TextView) findViewById(R.id.date_text);
        tv_week = (TextView) findViewById(R.id.week_text);
        tv_today = (TextView) findViewById(R.id.tv_today);
        monthDateView.setTextView(tv_date, tv_week);
        monthDateView.setDaysHasThingList(list);
        monthDateView.setDateClick(new MonthDateView.DateClick() {
            @Override
            public void onClickOnDate() {
                int month=monthDateView.getmSelMonth()+1;
                if(month<10){
                    if(monthDateView.getmSelDay()<10){
                        date = monthDateView.getmSelYear()+"-0"+month+"-0"+monthDateView.getmSelDay();
                    }
                    else{
                        date = monthDateView.getmSelYear()+"-0"+month+"-"+monthDateView.getmSelDay();
                    }
                }
                else{
                    if(monthDateView.getmSelDay()<10){
                        date = monthDateView.getmSelYear()+"-"+month+"-0"+monthDateView.getmSelDay();
                    }
                    else{
                        date = monthDateView.getmSelYear()+"-"+month+"-"+monthDateView.getmSelDay();
                    }
                }
                Intent backto_main=new Intent();
                backto_main.putExtra("Date",date);
                setResult(RESULT_OK,backto_main);
                finish();
                //Toast.makeText(getApplication(), date, Toast.LENGTH_SHORT).show();
            }
        });
        setOnlistener();

    }

    private void setOnlistener() {
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthDateView.onLeftClick();
            }
        });

        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthDateView.onRightClick();
            }
        });
        tv_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthDateView.setTodayToView();
            }
        });
    }
}
