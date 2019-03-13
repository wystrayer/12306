package com.example.a12306_final;

import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12306_final.Adapter.Order_unfinished_Adapter;
import com.example.a12306_final.javabean.Ord_tickets;
import com.example.a12306_final.javabean._User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Activity_Order extends TabActivity {
    TextView menu_deal,menu_sever,menu_query,menu_user;
    private TabHost tabhost;
    private RecyclerView recyclerView_Order,recyclerView_Order_finished;
    private List<Ord_tickets> mlist,mlist2;
    private List<String> objectID_list;
    private Order_unfinished_Adapter adapter;
    private String ID[];
    private String CurrentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_order_tab);
        Bmob.initialize(this,"22488e6103913f621999fda39c18cdec");
        //判断用户是否已登录
        _User userinfo = BmobUser.getCurrentUser(_User.class);
        if(userinfo == null){
            final AlertDialog.Builder Tixin=new AlertDialog.Builder(Activity_Order.this)
                    .setTitle("温馨提示")
                    .setMessage("请先登录")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Activity_Order.this,Login.class);
                            startActivity(intent);
                            finish();
                        }
                    });
            Tixin.show();
        }
        else {
            CurrentName=userinfo.getRealname();
        }
        tabhost = getTabHost();
        tabhost.addTab(tabhost.newTabSpec("one")
                .setIndicator("未完成订单").setContent(R.id.Order_unfinsihed));
        tabhost.addTab(tabhost.newTabSpec("two")
                .setIndicator("已完成订单").setContent(R.id.Order_finished));


        recyclerView_Order=(RecyclerView)findViewById(R.id.recycler_Order);
        recyclerView_Order_finished=(RecyclerView)findViewById(R.id.recycler_Order_finished);
        recyclerView_Order.setLayoutManager(new LinearLayoutManager(Activity_Order.this));
        recyclerView_Order_finished.setLayoutManager(new LinearLayoutManager(Activity_Order.this));
        mlist=new ArrayList<>();
        List<BmobQuery<Ord_tickets>> AndQuery=new ArrayList<BmobQuery<Ord_tickets>>();
        BmobQuery<Ord_tickets>q1=new BmobQuery<>();
        BmobQuery<Ord_tickets>q2=new BmobQuery<>();
        BmobQuery<Ord_tickets> queryList=new BmobQuery<>();
        q1.addWhereEqualTo("isFinished",false);
        q2.addWhereEqualTo("User_name",CurrentName);
        AndQuery.add(q1);
        AndQuery.add(q2);
        queryList.and(AndQuery);
        queryList.findObjects(new FindListener<Ord_tickets>() {
            @Override
            public void done(List<Ord_tickets> list, BmobException e) {
                if (e==null){
                    int i=0;
                    ID=new String[10];
                    for (Ord_tickets ord_tickets:list){
                        Ord_tickets ord_tickets1=new Ord_tickets(
                                ord_tickets.getStationID(),
                                ord_tickets.getUseTime(),
                                ord_tickets.getFirstPlace(),
                                ord_tickets.getLastPlace(),
                                ord_tickets.getFirstPlace_Time(),
                                ord_tickets.getLastPlace_Time(),
                                ord_tickets.getPrice(),
                                ord_tickets.getType(),
                                ord_tickets.getUser_ID(),
                                ord_tickets.getUser_name(),
                                ord_tickets.getIsFinished()
                        );
                        //Toast.makeText(getBaseContext(),ord_tickets.getObjectId(),Toast.LENGTH_LONG).show();
                        ID[i]=ord_tickets.getObjectId();
                        i++;
                        mlist.add(ord_tickets1);
                    }
                    showAllTickets(mlist,ID);
                }
            }
        });
        mlist2=new ArrayList<>();
        List<BmobQuery<Ord_tickets>> AndQuery2=new ArrayList<BmobQuery<Ord_tickets>>();
        BmobQuery<Ord_tickets> q3=new BmobQuery<>();
        BmobQuery<Ord_tickets> query2=new BmobQuery<>();
        q3.addWhereEqualTo("isFinished",true);
        AndQuery2.add(q3);
        AndQuery2.add(q2);
        query2.and(AndQuery2);
        query2.findObjects(new FindListener<Ord_tickets>() {
            @Override
            public void done(List<Ord_tickets> list, BmobException e) {
                if(e==null){
                    for (Ord_tickets ord_tickets:list) {
                        Ord_tickets ord_tickets2 = new Ord_tickets(
                                ord_tickets.getStationID(),
                                ord_tickets.getUseTime(),
                                ord_tickets.getFirstPlace(),
                                ord_tickets.getLastPlace(),
                                ord_tickets.getFirstPlace_Time(),
                                ord_tickets.getLastPlace_Time(),
                                ord_tickets.getPrice(),
                                ord_tickets.getType(),
                                ord_tickets.getUser_ID(),
                                ord_tickets.getUser_name(),
                                ord_tickets.getIsFinished()
                        );
                        mlist2.add(ord_tickets2);
                    }
                    showFinished(mlist2);
                }
            }
        });


        //目录监听
        menu_deal=(TextView)findViewById(R.id.menu_txt_deal);
        menu_sever=(TextView)findViewById(R.id.menu_txt_sever);
        menu_query=(TextView)findViewById(R.id.menu_txt_query);
        menu_user=(TextView)findViewById(R.id.menu_txt_user);
        menu_query.setSelected(true);
        menu_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!menu_deal.isSelected()){
                    menu_deal.setSelected(true);
                    menu_sever.setSelected(false);
                    menu_user.setSelected(false);
                    menu_query.setSelected(false);
                }
                Intent toMain=new Intent(Activity_Order.this,MainActivity.class);
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
                Intent toUser=new Intent(Activity_Order.this,My12306.class);
                startActivity(toUser);
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
                Intent toSever=new Intent(Activity_Order.this,Activity_server.class);
                startActivity(toSever);
                finish();
            }
        });
    }
    private void showFinished(final List<Ord_tickets> list){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter=new Order_unfinished_Adapter(Activity_Order.this,list);
                recyclerView_Order_finished.setAdapter(adapter);
                adapter.setOnItemClickListener(new Order_unfinished_Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                });
            }
        });

    }
    private void showAllTickets(final List<Ord_tickets> list,final String ID[]){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter=new Order_unfinished_Adapter(Activity_Order.this,list);
                recyclerView_Order.setAdapter(adapter);
                adapter.setOnItemClickListener(new Order_unfinished_Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent toPay=new Intent(Activity_Order.this, pay_Order.class);
                        String objectid=ID[position];
                        toPay.putExtra("objected",objectid);
                        startActivity(toPay);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
