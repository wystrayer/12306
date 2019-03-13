package com.example.a12306_final.javabean;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by 老汉 on 2017/12/5.
 */

public class Tickets extends BmobObject{
    private String StationID;
    private String useTime;
    private String FirstPlace;
    private String LastPlace;
    private String FirstPlace_Time;
    private String LastPlace_Time;
    private String FirstPlace_Type;
    private String LastPlace_Type;
    private String Shangwu_price;
    private String Erdeng_price;
    private String Yideng_price;
    private Number Shangwu_num;
    private Number Yideng_num;
    private Number Erdeng_num;
    private Number Wuzuo_num;

    public Tickets(){

    };
    public Tickets(String StationID, String useTime, String FirstPlace, String LastPlace, String FirstPlace_Time, String LastPlace_Time,
                   String FirstPlace_Type, String LastPlace_Type,String Shangwu_price,String Yideng_price,String Erdeng_price,
                   Number Shangwu_num, Number Yideng_num, Number Erdeng_num, Number Wuzuo_num){
        this.StationID=StationID;
        this.useTime=useTime;
        this.FirstPlace=FirstPlace;
        this.LastPlace=LastPlace;
        this.FirstPlace_Time=FirstPlace_Time;
        this.LastPlace_Time=LastPlace_Time;
        this.FirstPlace_Type=FirstPlace_Type;
        this.LastPlace_Type=LastPlace_Type;
        this.Shangwu_price=Shangwu_price;
        this.Erdeng_price=Erdeng_price;
        this.Yideng_price=Yideng_price;
        this.Shangwu_num=Shangwu_num;
        this.Yideng_num=Yideng_num;
        this.Erdeng_num=Erdeng_num;
        this.Wuzuo_num=Wuzuo_num;
    }
    public String getUseTime(){return this.useTime;}
    public String getStationID(){return this.StationID;}
    public String getFirstPlace(){return this.FirstPlace;}
    public String getLastPlace(){return this.LastPlace;}
    public String getFirstPlace_Type(){return this.FirstPlace_Type;}
    public String getFirstPlace_Time(){return this.FirstPlace_Time;}
    public String getLastPlace_Time(){return this.LastPlace_Time;}
    public String getLastPlace_Type(){return this.LastPlace_Type;}
    public String getShangwu_price(){return this.Shangwu_price;}
    public String getErdeng_price(){return this.Erdeng_price;}
    public String getYideng_price(){return this.Yideng_price;}
    public Number getShangwu_num(){return this.Shangwu_num;}
    public Number getYideng_num(){return this.Yideng_num;}
    public Number getErdeng_num(){return this.Erdeng_num;}
    public Number getWuzuo_num(){return this.Wuzuo_num;}
    public void setShangwu_num(Number num){this.Shangwu_num=num;}
    public void setYideng_num(Number num){this.Yideng_num=num;}
    public void setErdeng_num(Number num){this.Erdeng_num=num;}
    public void setWuzuo_num(Number num){this.Wuzuo_num=num;}

}
