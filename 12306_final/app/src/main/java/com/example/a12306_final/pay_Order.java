package com.example.a12306_final;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12306_final.javabean.Ord_tickets;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class pay_Order extends AppCompatActivity {
    private String objectId,ID;
    private Ord_tickets order;
    private TextView Pay_FirstPlace,Pay_LastPlace,Pay_FirstPlace_Time,Pay_LastPlace_Time,Pay_useTime,Pay_StationID;
    private TextView Pay_price,Pay_type,User_name,User_ID,Pay_Sum;
    private Button Pay_rightaway,Pay_cancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_pay_order);
        Intent getObjectId=getIntent();
        objectId=getObjectId.getStringExtra("objected");
        findView();
        Bmob.initialize(this,"22488e6103913f621999fda39c18cdec");
        //查询订单
        BmobQuery<Ord_tickets> query=new BmobQuery<Ord_tickets>();
        query.addWhereEqualTo("objectId",objectId);
        query.findObjects(new FindListener<Ord_tickets>() {
            @Override
            public void done(List<Ord_tickets> list, BmobException e) {
                if(e==null){
                    for (Ord_tickets ord_tickets :list){
                        ID=ord_tickets.getObjectId();
                        order=new Ord_tickets(
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
                    }
                    Pay_FirstPlace.setText(order.getFirstPlace());
                    Pay_FirstPlace_Time.setText(order.getFirstPlace_Time());
                    Pay_LastPlace.setText(order.getLastPlace());
                    Pay_LastPlace_Time.setText(order.getLastPlace_Time());
                    String t=order.getType()+"座";
                    Pay_type.setText(t);
                    String a=order.getPrice()+"元";
                    Pay_price.setText(a);
                    Pay_Sum.setText(a);
                    Pay_useTime.setText(order.getUseTime());
                    Pay_StationID.setText(order.getStationID());
                    User_name.setText(order.getUser_name());
                    User_ID.setText(order.getUser_ID());
                    Listener(ID);
                }
            }
        });

    }
    private void findView(){
        Pay_FirstPlace=(TextView)findViewById(R.id.Pay_FirstStation_name);
        Pay_FirstPlace_Time=(TextView)findViewById(R.id.Pay_FirstStation_time);
        Pay_LastPlace=(TextView)findViewById(R.id.Pay_LastStation_name);
        Pay_LastPlace_Time=(TextView)findViewById(R.id.Pay_LastStation_time);
        Pay_price=(TextView)findViewById(R.id.Pay_Price);
        Pay_type=(TextView)findViewById(R.id.Pay_UserSeatType);
        Pay_useTime=(TextView)findViewById(R.id.Pay_station_useTime);
        Pay_StationID=(TextView)findViewById(R.id.Pay_station_id);
        User_ID=(TextView)findViewById(R.id.Pay_UserID);
        User_name=(TextView)findViewById(R.id.Pay_UserName);
        Pay_Sum=(TextView)findViewById(R.id.Pay_Sum);
        Pay_rightaway=(Button)findViewById(R.id.Pay_rightaway);
        Pay_cancle=(Button)findViewById(R.id.Pay_cancle);
    }
    private void Listener(final String ObjectID){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //订单成功
                Pay_rightaway.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Toast.makeText(getBaseContext(),ObjectID,Toast.LENGTH_LONG).show();
                        Pay_rightaway.setEnabled(false);
                        order.setIsFinished(true);
                        order.update(ObjectID, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(getBaseContext(),"支付成功",Toast.LENGTH_LONG).show();
                                    Intent toMain=new Intent(pay_Order.this, MainActivity.class);
                                    startActivity(toMain);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getBaseContext(),"支付失败",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                //取消订单
                Pay_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Ord_tickets ord_tickets=new Ord_tickets();
                        ord_tickets.setObjectId(ObjectID);
                        new AlertDialog.Builder(pay_Order.this).setTitle("确认取消订单嘛？")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ord_tickets.delete(ObjectID, new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if(e==null){
                                                    Toast.makeText(getBaseContext(),"取消成功",Toast.LENGTH_LONG).show();
                                                    Intent toMain=new Intent(pay_Order.this, MainActivity.class);
                                                    startActivity(toMain);
                                                    finish();
                                                }
                                                else{
                                                    Toast.makeText(getBaseContext(),"取消失败",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                }).show();
                    }
                });
            }
        });
    }
}
