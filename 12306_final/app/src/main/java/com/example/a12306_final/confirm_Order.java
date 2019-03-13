package com.example.a12306_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12306_final.javabean.Tickets;
import com.example.a12306_final.javabean._User;
import com.example.a12306_final.javabean.Ord_tickets;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class confirm_Order extends AppCompatActivity {
    private Boolean flag;
    private String stationID,UserID,User_name,Selected_price,Order_objectId;
    private Tickets tickets1;
    private TextView FirstPlace_name,FirstPlace_Time,LastPlace_name,LastPlace_Time,Order_station_ID,useTime;
    private TextView Order_userSeatType,Order_userID,Order_userName;
    private Button shangwu,yideng,erdeng,wuzuo,btn_submit;
    private Number tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_order_confirm);
        findView();
        Bmob.initialize(this,"22488e6103913f621999fda39c18cdec");
        _User userinfo = BmobUser.getCurrentUser(_User.class);
        if(userinfo != null){
            User_name = (String) BmobUser.getObjectByKey("realname");
            UserID=(String)BmobUser.getObjectByKey("userId");
            Order_userName.setText(User_name);
            if (!UserID.isEmpty()){
                StringBuilder stringBuilder=new StringBuilder();
                for(int i=0;i<UserID.length();i++){
                    char s=UserID.charAt(i);
                    if(i>=3&&i<=13){
                        stringBuilder.append("*");
                    }else {
                        stringBuilder.append(s);
                    }
                }
                Order_userID.setText(stringBuilder.toString());
            }
        }
        Intent getData=getIntent();
        stationID=getData.getStringExtra("stationID");
        BmobQuery<Tickets>query=new BmobQuery<Tickets>();
        query.addWhereEqualTo("StationID",stationID);
        query.setLimit(1);
        query.findObjects(new FindListener<Tickets>(){
            @Override
            public void done(List<Tickets> list, BmobException e) {
                if(e==null){
                    for (Tickets tickets : list){
                        Order_objectId=tickets.getObjectId();
                        FirstPlace_name.setText(tickets.getFirstPlace());
                        LastPlace_name.setText(tickets.getLastPlace());
                        String time=tickets.getFirstPlace_Time()+" "+"出发";
                        FirstPlace_Time.setText(time);
                        time=tickets.getLastPlace_Time()+" "+"终点";
                        LastPlace_Time.setText(time);
                        useTime.setText(tickets.getUseTime());
                        Order_station_ID.setText(tickets.getStationID());
                        String tmp1="商务"+" "+judge(tickets.getShangwu_num().intValue())+"\n"+tickets.getShangwu_price();
                        String tmp2="一等"+" "+judge(tickets.getYideng_num().intValue())+"\n"+tickets.getYideng_price();
                        String tmp3="二等"+" "+judge(tickets.getErdeng_num().intValue())+"\n"+tickets.getErdeng_price();
                        String tmp4="无座"+" "+judge(tickets.getWuzuo_num().intValue())+"\n"+tickets.getErdeng_price();
                        shangwu.setText(tmp1);
                        yideng.setText(tmp2);
                        erdeng.setText(tmp3);
                        wuzuo.setText(tmp4);
                        tickets1=new Tickets(
                                tickets.getStationID(),
                                tickets.getUseTime(),
                                tickets.getFirstPlace(),
                                tickets.getLastPlace(),
                                tickets.getFirstPlace_Time(),
                                tickets.getLastPlace_Time(),
                                tickets.getFirstPlace_Type(),
                                tickets.getLastPlace_Type(),
                                tickets.getShangwu_price(),
                                tickets.getYideng_price(),
                                tickets.getErdeng_price(),
                                tickets.getShangwu_num(),
                                tickets.getYideng_num(),
                                tickets.getErdeng_num(),
                                tickets.getWuzuo_num()
                        );
                        if(tickets1.getShangwu_num().intValue()==0){
                            shangwu.setEnabled(false);
                            shangwu.setTextColor(getResources().getColor(R.color.text_gray));
                        }
                        if(tickets1.getErdeng_num().intValue()==0){
                            erdeng.setEnabled(false);
                            erdeng.setTextColor(getResources().getColor(R.color.text_gray));
                        }
                        if(tickets1.getYideng_num().intValue()==0){
                            yideng.setEnabled(false);
                            yideng.setTextColor(getResources().getColor(R.color.text_gray));
                        }
                        if(tickets1.getWuzuo_num().intValue()==0){
                            wuzuo.setEnabled(false);
                            wuzuo.setTextColor(getResources().getColor(R.color.text_gray));
                        }
                        erdeng.setSelected(true);
                        Selected_price=tickets1.getErdeng_price();
                        //Toast.makeText(getBaseContext(),tickets1.getTest(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        //选择监听
        shangwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!shangwu.isSelected()){
                    shangwu.setSelected(true);
                    yideng.setSelected(false);
                    erdeng.setSelected(false);
                    wuzuo.setSelected(false);
                    Order_userSeatType.setText(shangwu.getText().subSequence(0,2));
                    Selected_price=tickets1.getShangwu_price();
                    //Toast.makeText(getBaseContext(),Selected_price,Toast.LENGTH_LONG).show();
                }
            }
        });
        yideng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!yideng.isSelected()){
                    shangwu.setSelected(false);
                    yideng.setSelected(true);
                    erdeng.setSelected(false);
                    wuzuo.setSelected(false);
                    Order_userSeatType.setText(yideng.getText().subSequence(0,2));
                    Selected_price=tickets1.getYideng_price();
                }
            }
        });
        erdeng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!erdeng.isSelected()){
                    shangwu.setSelected(false);
                    yideng.setSelected(false);
                    erdeng.setSelected(true);
                    wuzuo.setSelected(false);
                    Order_userSeatType.setText(erdeng.getText().subSequence(0,2));
                    Selected_price=tickets1.getErdeng_price();
                }
            }
        });
        wuzuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!wuzuo.isSelected()){
                    shangwu.setSelected(false);
                    yideng.setSelected(false);
                    erdeng.setSelected(false);
                    wuzuo.setSelected(true);
                    Order_userSeatType.setText(wuzuo.getText().subSequence(0,2));
                    Selected_price=tickets1.getErdeng_price();
                }
            }
        });
        //提交
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_submit.setEnabled(false);//防止多次点击
                //票数减一
                Tickets tickets=new Tickets();
                if(shangwu.isSelected()&&tickets1.getShangwu_num().intValue()>0){
                    tmp=tickets1.getShangwu_num().intValue()-1;
                    tickets.setShangwu_num(tmp);
                    tickets.update(Order_objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                }else if (yideng.isSelected()&&tickets1.getYideng_num().intValue()>0){
                    tmp=tickets1.getYideng_num().intValue()-1;
                    tickets.setYideng_num(tmp);
                    tickets.update(Order_objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                }else if(erdeng.isSelected()&&tickets1.getErdeng_num().intValue()>0){
                    tmp=tickets1.getYideng_num().intValue()-1;
                    tickets.setErdeng_num(tmp);
                    tickets.update(Order_objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                }else if (wuzuo.isSelected()&&tickets1.getWuzuo_num().intValue()>0){
                    tmp=tickets1.getWuzuo_num().intValue()-1;
                    tickets.setWuzuo_num(tmp);
                    tickets.update(Order_objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                }
                //创建订单
                final Ord_tickets ord_tickets=new Ord_tickets(
                            tickets1.getStationID(),
                            tickets1.getUseTime(),
                            tickets1.getFirstPlace(),
                            tickets1.getLastPlace(),
                            FirstPlace_Time.getText().toString(),
                            LastPlace_Time.getText().toString(),
                            Selected_price,
                            Order_userSeatType.getText().toString(),
                            Order_userID.getText().toString(),
                            User_name,
                            false
                    );
                ord_tickets.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            //Toast.makeText(getBaseContext(),ord_tickets.getObjectId(),Toast.LENGTH_LONG).show();
                            Intent toPay=new Intent(confirm_Order.this,pay_Order.class);
                            toPay.putExtra("objected",ord_tickets.getObjectId());
                            startActivity(toPay);
                            finish();
                        }
                        else {
                            Toast.makeText(getBaseContext(),"添加失败",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
    private void findView(){
        FirstPlace_name=(TextView)findViewById(R.id.Order_FirstStation_name);
        FirstPlace_Time=(TextView)findViewById(R.id.Order_FirstStation_time);
        LastPlace_name=(TextView)findViewById(R.id.Order_LastStation_name);
        LastPlace_Time=(TextView)findViewById(R.id.Order_LastStation_time);
        Order_station_ID=(TextView)findViewById(R.id.Order_station_id);
        useTime=(TextView)findViewById(R.id.Order_station_useTime);
        shangwu=(Button)findViewById(R.id.Order_btn_shangwu);
        yideng=(Button)findViewById(R.id.Order_btn_yideng);
        erdeng=(Button)findViewById(R.id.Order_btn_erdeng);
        wuzuo=(Button)findViewById(R.id.Order_btn_wuzuo);
        btn_submit=(Button)findViewById(R.id.Odrer_submit);
        Order_userSeatType=(TextView)findViewById(R.id.Order_UserSeatType);
        Order_userID=(TextView)findViewById(R.id.Order_UserID);
        Order_userName=(TextView)findViewById(R.id.Order_UserName);
        btn_submit=(Button)findViewById(R.id.Odrer_submit);
    }
    public String judge(int Num){
        if(Num>0)return "有";
        else return "无";
    }
    @Override
    public void onBackPressed() {
        finish();
    }

}
