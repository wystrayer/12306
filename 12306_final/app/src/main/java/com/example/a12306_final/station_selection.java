package com.example.a12306_final;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12306_final.Adapter.RecyclerView_stationAdapter;
import com.example.a12306_final.javabean.Stations;
import com.example.a12306_final.javabean._User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class station_selection extends AppCompatActivity {
    private EditText station_select;
    private RecyclerView recyclerView_station;
    private RecyclerView_stationAdapter mAdapter;
    private List<String> mList;
    private List<String> mList_usual;
    private SharedPreferences pref;
    private ImageView imageView_search;
    private String nameforsearch;
    private TextView station_usually_use;
    private TextView station_list;
    private TextView menu_deal,menu_sever,menu_query,menu_user;
    private boolean first_start =true;
    boolean same_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.station_selection);
        Bmob.initialize(this,"22488e6103913f621999fda39c18cdec");

        station_select = (EditText) findViewById(R.id.editText_station);
        recyclerView_station = (RecyclerView) findViewById(R.id.recycler_station);
        imageView_search = (ImageView)findViewById(R.id.image_search);
        station_list = (TextView) findViewById(R.id.StationList);
        station_usually_use = (TextView) findViewById(R.id.Usualluy_use);
        recyclerView_station.setLayoutManager(new LinearLayoutManager(station_selection.this));
        station_usually_use.setSelected(true);
       //默认执行当前活动时加载常用车站列表
        mList_usual = new ArrayList<String>();
        HistoryLoad();
        showAllStations(mList_usual);
        //点击常用车站时显示常用车站列表
        station_usually_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!station_usually_use.isSelected()){
                    station_usually_use.setSelected(true);
                    station_list.setSelected(false);
                }

                HistoryLoad();
                showAllStations(mList_usual);
            }
        });

        //点击车站列表时显示所有车站
        station_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!station_list.isSelected()){
                    station_list.setSelected(true);
                    station_usually_use.setSelected(false);
                }
                String[] StationList=getResources().getStringArray(R.array.city);
                mList = new ArrayList<>();
                for (int i=0;i<StationList.length;i++){
                    mList.add(StationList[i]);
                }
                showAllStations(mList);
               /* BmobQuery<Stations> query_list = new BmobQuery<>();
                query_list.addWhereNotEqualTo("station_name","");
                query_list.order("-station_name");
                query_list.findObjects(new FindListener<Stations>() {
                    @Override
                    public void done(List<Stations> list, BmobException e) {
                        if(e == null){
                            mList = new ArrayList<>();
                            for(Stations stations : list){
                                String name_station = stations.getStation_name();
                                mList.add(name_station);
                            }
                            showAllStations(mList);
                        }
                    }
                });*/
            }
        });

        //点击搜索时显示查询到的信息
        imageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameforsearch = station_select.getText().toString();
                //Toast.makeText(getBaseContext(),"程序运行到这里",Toast.LENGTH_SHORT).show();
                if(!nameforsearch.equals("")) {
                    add_mList_usual(nameforsearch);
                    HistorySave();
                    String[] StationList=getResources().getStringArray(R.array.city);
                    for(int j = 0;j < StationList.length; ++j){
                        if(StationList[j].equals(nameforsearch)){
                            mList=new ArrayList<String>();
                            mList.add(0,nameforsearch);
                            break;
                        }
                    }
                    showAllStations(mList);
                }
                else {
                    Toast.makeText(getBaseContext(),"请输入车站信息",Toast.LENGTH_SHORT).show();
                }
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
                Intent intent = new Intent(station_selection.this,MainActivity.class);
                startActivity(intent);
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
                pref= getSharedPreferences("date_info",MODE_PRIVATE);
                _User userinfo = BmobUser.getCurrentUser(_User.class);
                boolean isAutologin= pref.getBoolean("autologin",false);
                boolean isRemember= pref.getBoolean("remember_password",false);
                if((userinfo != null && isAutologin == true) && isRemember == true){
                    Intent my12306_activity = new Intent(station_selection.this, My12306_Logined.class);
                    startActivity(my12306_activity);
                }
                else {
                    Intent my12306_activity = new Intent(station_selection.this, My12306.class);
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
            }
        });

    }

    public void HistorySave(){

        // 文件
        FileOutputStream _historyOut;
        BufferedWriter _historyWriter;
        // 文件
        try {
            _historyOut = openFileOutput("station_history",
                    Context.MODE_PRIVATE);

            _historyWriter = new BufferedWriter(
                    new OutputStreamWriter(_historyOut));
            // 循环，写入
            Iterator< String > iter = mList_usual.iterator();
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
            in = openFileInput("station_history");
            reader = new BufferedReader(
                    new InputStreamReader( in )
            );
            mList_usual.clear();
            String dataStr = "";
            String line = reader.readLine();
            while( line != null ){
                mList_usual.add( line );
                line = reader.readLine();
            }
        }
        catch ( IOException ex ){
            ex.printStackTrace();
            //是否是第一次启动
            if(first_start == true) {
                HistorySave();
                first_start=false;
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
    private void add_mList_usual(String nameforsearch){
        //判断是否已经有相同的查询记录
        Iterator< String > iter = mList_usual.iterator();
        if(!iter.hasNext()) {
            mList_usual.add(0, nameforsearch);
        }
        else {
            while (iter.hasNext()) {
                String stri = iter.next();
                if (stri.equals(nameforsearch)) {
                    same_date = true;
                    iter.remove();
                    mList_usual.add(0, nameforsearch);
                    break;
                } else {
                    same_date = false;
                }
            }
            if (!same_date)
                mList_usual.add(0, nameforsearch);
        }

        //判断是否超过十条记录
        Iterator< String > iterfirst = mList_usual.iterator();
        int i=0;
        while(iterfirst.hasNext())
        {
            String sam = iterfirst.next();
            i++;
            if(i>10)
            {
                iterfirst.remove();
            }
        }
    }
//recycler的点击事件
   private void showAllStations(final List<String> list){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter = new RecyclerView_stationAdapter(station_selection.this,list);
                recyclerView_station.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new RecyclerView_stationAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                       // Toast.makeText(getBaseContext(), list.get(position), Toast.LENGTH_SHORT).show();
                        //获得前一个活动传过来的数据
                        Intent place = getIntent();
                        String station = place.getStringExtra("station");
                        if (station.equals("place_first")) {
                            Intent intent_to_main = new Intent();
                            intent_to_main.putExtra("station_select", list.get(position));
                            //Toast.makeText(getBaseContext(), list.get(position), Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK,intent_to_main);
                            finish();
                        }
                        if (station.equals("place_last")) {
                            Intent intent_to_main = new Intent();
                            intent_to_main.putExtra("station_select", list.get(position));
                            //Toast.makeText(getBaseContext(), list.get(position), Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK,intent_to_main);
                            finish();
                        }
                    }
                });

            }
        });
    }

}
