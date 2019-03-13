package com.example.a12306_final;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12306_final.assets.Activity_Calendar;
import com.example.a12306_final.javabean._User;
import com.example.a12306_final.Adapter.RecyclerView_stationAdapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

import static android.media.CamcorderProfile.get;

public class MainActivity extends AppCompatActivity {
    static String returnData;
    private SharedPreferences pref;
    private RecyclerView route;
    private RecyclerView_stationAdapter madapter;
    private List<String> mlist;
    private boolean same_date;
    private static boolean first_start=true;
    TextView menu_deal,menu_sever,menu_query,menu_user,textview_Date,textView_time,textView_xibie;
    TextView place_first,place_last;
    TextView zitou_all,zitou_GDC,zitou_Z,zitou_T,zitou_K,zitou_other;
    ImageButton jiaohuan;
    Button btn_chaxun;
    private String FirstPlace,LastPlace,Begin_Day,Begin_Time;
    final CharSequence[] Time={
            "00:00--24:00",
            "00:00--06:00",
            "06:00--12:00",
            "12:00--18:00",
            "18:00--24:00"};
    final CharSequence[] Xibie={
            "不限",
            "商务座",
            "特等座",
            "一等座",
            "二等座",
            "高级软座",
            "软卧",
            "硬卧",
            "软座",
            "硬座"};
    static int YourChoice1,YourChoice2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this,"22488e6103913f621999fda39c18cdec");

        //循环列表
        route = (RecyclerView) findViewById(R.id.route_usual);
        route.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        //设置系统日期
        textview_Date=(TextView) findViewById(R.id.textview_Date);
        Calendar c=Calendar.getInstance();
        String date;
        if((c.get(Calendar.MONTH)+1)<10){
            if((c.get(Calendar.DAY_OF_MONTH))<10){
                date = c.get(Calendar.YEAR)+"-0"+(c.get(Calendar.MONTH)+1)+"-0"+(c.get(Calendar.DAY_OF_MONTH));
            }
            else{
                date = c.get(Calendar.YEAR)+"-0"+(c.get(Calendar.MONTH)+1)+"-"+(c.get(Calendar.DAY_OF_MONTH));
            }
        }
        else{
            if((c.get(Calendar.DAY_OF_MONTH))<10){
                date = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-0"+(c.get(Calendar.DAY_OF_MONTH));
            }
            else{
                date = c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+(c.get(Calendar.DAY_OF_MONTH));
            }
        }
        if(returnData!=null)
            textview_Date.setText(returnData);
        else
            textview_Date.setText(date);
        textview_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calender_post=new Intent(MainActivity.this,Activity_Calendar.class);
                startActivityForResult(calender_post,1);
            }
        });

        //出发时间选择
        YourChoice1=-1;
        textView_time=(TextView)findViewById(R.id.textview_Time);
        textView_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.textview_Time:
                        AlertDialog.Builder TimeChoice=new AlertDialog.Builder(MainActivity.this);
                        TimeChoice.setSingleChoiceItems(Time, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                YourChoice1=which;
                            }
                        });
                        TimeChoice.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(YourChoice1!=-1){
                                    textView_time.setText(Time[YourChoice1]);
                                }
                            }
                        });
                        TimeChoice.show();
                        break;
                    default:
                        break;

                }

            }
        });


        //席别选择
        YourChoice2=-1;
        textView_xibie=(TextView)findViewById(R.id.textview_Xibie);
        textView_xibie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.textview_Xibie:
                    AlertDialog.Builder XibieChoice=new AlertDialog.Builder(MainActivity.this);
                    XibieChoice.setSingleChoiceItems(Xibie, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            YourChoice2=which;
                        }
                    });
                    XibieChoice.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(YourChoice2!=-1){
                                textView_xibie.setText(Xibie[YourChoice2]);
                            }
                        }
                    });
                    XibieChoice.show();
                    break;
                    default:
                        break;
                }
            }
        });
        //车型监听
        zitou_all=(TextView)findViewById(R.id.zitou_all);
        zitou_GDC=(TextView)findViewById(R.id.zitou_GDC);
        zitou_K=(TextView)findViewById(R.id.zitou_K);
        zitou_T=(TextView)findViewById(R.id.zitou_T);
        zitou_Z=(TextView)findViewById(R.id.zitou_Z);
        zitou_other=(TextView)findViewById(R.id.zitou_other);
        zitou_all.setSelected(true);
        zitouListenr();
        // 站点交换
        jiaohuan=(ImageButton)findViewById(R.id.button_Jiaohuan);
        jiaohuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place_first=(TextView)findViewById(R.id.place_1);
                place_last=(TextView)findViewById(R.id.place_2);
                String tmp1,tmp2;
                tmp1=place_first.getText().toString();
                tmp2=place_last.getText().toString();
                place_first.setText(tmp2);
                place_last.setText(tmp1);
            }
        });

        //目录监听
        menu_deal=(TextView)findViewById(R.id.menu_txt_deal);
        menu_sever=(TextView)findViewById(R.id.menu_txt_sever);
        menu_query=(TextView)findViewById(R.id.menu_txt_query);
        menu_user=(TextView)findViewById(R.id.menu_txt_user);
        menu_deal.setSelected(true);
        menu_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!menu_deal.isSelected()){
                    menu_deal.setSelected(true);
                    menu_sever.setSelected(false);
                    menu_user.setSelected(false);
                    menu_query.setSelected(false);
                }
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
                Intent toOrder=new Intent(MainActivity.this, Activity_Order.class);
                startActivity(toOrder);
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
                pref= getSharedPreferences("date_info",MODE_PRIVATE);
                _User userinfo = BmobUser.getCurrentUser(_User.class);
                boolean isAutologin= pref.getBoolean("autologin",false);
                boolean isRemember= pref.getBoolean("remember_password",false);
                if((userinfo != null && isAutologin == true) && isRemember == true){
                    Intent my12306_activity = new Intent(MainActivity.this, My12306_Logined.class);
                    startActivity(my12306_activity);
                }
                else {
                    Intent my12306_activity = new Intent(MainActivity.this, My12306.class);
                    startActivity(my12306_activity);
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
                Intent toServer=new Intent(MainActivity.this, Activity_server.class);
                startActivity(toServer);
                finish();
            }
        });

        //地点选择监听
        place_first=(TextView) findViewById(R.id.place_1);
        place_last=(TextView)findViewById(R.id.place_2);
        final Intent intent_to_station_selection=new Intent(MainActivity.this,station_selection.class);
        place_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_to_station_selection.putExtra("station","place_first");
                startActivityForResult(intent_to_station_selection,2);
            }
        });
        place_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_to_station_selection.putExtra("station","place_last");
                startActivityForResult(intent_to_station_selection,3);
            }
        });

        //加载历史查询路线
        mlist = new ArrayList<String>();
        HistoryLoad();
        if(!mlist.isEmpty()) {
            String[] place_to_set = mlist.get(0).split("--");
            place_first.setText(place_to_set[0].toString());
            place_last.setText(place_to_set[1].toString());
        }
        first_start = false;
        showHistory(mlist);

        //查询按钮
        btn_chaxun=(Button)findViewById(R.id.button_Chaxun);
        btn_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstPlace=place_first.getText().toString();
                LastPlace=place_last.getText().toString();
                Begin_Day=textview_Date.getText().toString();
                Begin_Time=textView_time.getText().toString();
                String str = String.format("%s--%s",place_first.getText().toString(),place_last.getText().toString());
                Iterator< String > iter = mlist.iterator();
                if(!iter.hasNext()) {
                    mlist.add(0,str);
                }
                else {
                    while (iter.hasNext()) {
                        String stri = iter.next();
                        if (stri.equals(str)) {
                            same_date = true;
                            iter.remove();
                            mlist.add(0, str);
                            break;
                        } else {
                            same_date = false;
                        }
                    }
                    if (!same_date)
                        mlist.add(0, str);
                }

                //判断是否超过五条记录
                Iterator< String > iterfirst = mlist.iterator();
                int i=0;
                while(iterfirst.hasNext())
                {
                    String sam = iterfirst.next();
                    i++;
                    if(i>5)
                    {
                        iterfirst.remove();
                    }
                }
                HistorySave();
                showHistory(mlist);
                Intent intent_to_station_query=new Intent(MainActivity.this,query.class);
                intent_to_station_query.putExtra("FirstPlace",FirstPlace);
                intent_to_station_query.putExtra("LastPlace",LastPlace);
                intent_to_station_query.putExtra("Begin_Day",Begin_Day);
                intent_to_station_query.putExtra("Begin_Time",Begin_Time);
                startActivity(intent_to_station_query);
            }
        });


    }

    private void showHistory(final List<String> list){
        madapter = new RecyclerView_stationAdapter(MainActivity.this,list);
        route.setAdapter(madapter);
        madapter.setOnItemClickListener(new RecyclerView_stationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getBaseContext(),list.get(position),Toast.LENGTH_SHORT).show();
                String luxian=list.get(position);
                String [] station = luxian.split("--");
                place_first.setText(station[0].toString());
                place_last.setText(station[1].toString());
            }
        });
    }

    public void HistorySave(){

        // 文件
        FileOutputStream _historyOut;
        BufferedWriter _historyWriter;
        // 文件
        try {
            _historyOut = openFileOutput("route_history",
                    Context.MODE_PRIVATE);

            _historyWriter = new BufferedWriter(
                    new OutputStreamWriter(_historyOut));
            // 循环，写入
            Iterator< String > iter = mlist.iterator();
            while ( iter.hasNext() ){
                String str = iter.next();
                _historyWriter.write( str );
                _historyWriter.newLine();
            }
            _historyWriter.flush();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
        }
    }

    public void HistoryLoad(){
        FileInputStream in = null;
        BufferedReader reader = null;
        try {
            in = openFileInput("route_history");
            reader = new BufferedReader(
                    new InputStreamReader( in )
            );
            mlist.clear();
            String dataStr = "";
            String line = reader.readLine();
            while( line != null ){
                mlist.add( line );
                line = reader.readLine();
            }
        }
        catch ( IOException ex ){
            ex.printStackTrace();
            //是否是第一次启动
            if(first_start == true) {
                HistorySave();
            }
        }
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }
            catch ( IOException ex ){
                ex.printStackTrace();
            }
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    returnData=data.getStringExtra("Date");
                    textview_Date=(TextView) findViewById(R.id.textview_Date);
                    textview_Date.setText(returnData);
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    String str = data.getStringExtra("station_select");
                    place_first.setText(str);
                }

                    break;
            case 3:
                if(resultCode == RESULT_OK){
                    String str = data.getStringExtra("station_select");
                    place_last.setText(str);
                }
                    break;
            default:
        }
    }
    public void zitouListenr(){
        zitou_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!zitou_all.isSelected()){
                    zitou_all.setSelected(true);
                    zitou_Z.setSelected(false);
                    zitou_other.setSelected(false);
                    zitou_T.setSelected(false);
                    zitou_K.setSelected(false);
                    zitou_GDC.setSelected(false);
                }
                else{
                    zitou_all.setSelected(false);
                }
            }
        });
        zitou_GDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!zitou_GDC.isSelected()){
                    zitou_all.setSelected(false);
                    zitou_GDC.setSelected(true);

                }
                else {
                    zitou_GDC.setSelected(false);
                }
            }
        });
        zitou_K.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!zitou_K.isSelected()){
                    zitou_all.setSelected(false);
                    zitou_K.setSelected(true);
                }
                else {
                    zitou_K.setSelected(false);
                }
            }
        });
        zitou_Z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!zitou_Z.isSelected()){
                    zitou_Z.setSelected(true);
                    zitou_all.setSelected(false);
                }
                else {
                    zitou_Z.setSelected(false);
                }
            }
        });
        zitou_T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!zitou_T.isSelected()){
                    zitou_T.setSelected(true);
                    zitou_all.setSelected(false);
                }
                else {
                    zitou_T.setSelected(false);
                }
            }
        });
        zitou_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!zitou_other.isSelected()){
                    zitou_other.setSelected(true);
                    zitou_all.setSelected(false);
                }
                else {
                    zitou_other.setSelected(false);
                }
            }
        });
    }
}
