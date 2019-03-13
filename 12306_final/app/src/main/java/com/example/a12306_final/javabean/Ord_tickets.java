package com.example.a12306_final.javabean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 老汉 on 2017/12/9.
 */

public class Ord_tickets extends BmobObject {
    private String StationID;
    private String useTime;
    private String FirstPlace;
    private String LastPlace;
    private String FirstPlace_Time;
    private String LastPlace_Time;
    private String Price;
    private String Type;
    private String User_ID;
    private String User_name;
    private Boolean isFinished;

    public Ord_tickets(){}
    public Ord_tickets(String StationID,String useTime,String FirstPlace,String LastPlace,String FirstPlace_Time,String LastPlace_Time,
                       String Price,String Type,String User_ID,String User_name,Boolean isFinished){
        this.StationID=StationID;
        this.useTime=useTime;
        this.FirstPlace=FirstPlace;
        this.LastPlace=LastPlace;
        this.FirstPlace=FirstPlace;
        this.FirstPlace_Time=FirstPlace_Time;
        this.LastPlace_Time=LastPlace_Time;
        this.Price=Price;
        this.Type=Type;
        this.User_ID=User_ID;
        this.User_name=User_name;
        this.isFinished=isFinished;
    }

    public String getStationID(){return this.StationID;}
    public String getUseTime(){return this.useTime;}
    public String getFirstPlace(){return this.FirstPlace;}
    public String getLastPlace(){return this.LastPlace;}
    public String getFirstPlace_Time(){return this.FirstPlace_Time;}
    public String getLastPlace_Time(){return this.LastPlace_Time;}
    public String getPrice(){return this.Price;}
    public String getType(){return  this.Type;}
    public String getUser_ID(){return this.User_ID;}
    public String getUser_name(){return this.User_name;}
    public Boolean getIsFinished(){return this.isFinished;}

    public void setStationID(String StationID){this.StationID=StationID;}
    public void setUseTime(String UseTime){this.useTime=UseTime;}
    public void setFirstPlace(String FirstPlace){this.FirstPlace=FirstPlace;}
    public void setLastPlace(String LastPlace){this.LastPlace=LastPlace;}
    public void setFirstPlace_Time(String FirstPlace_Time){this.FirstPlace_Time=FirstPlace_Time;}
    public void setLastPlace_Time(String LastPlace_Time){this.LastPlace_Time=LastPlace_Time;}
    public void setPrice(String price){this.Price=price;}
    public void setType(String type){this.Type=type;}
    public void setUser_ID(String id){this.User_ID=id;}
    public void setUser_name(String nm){this.User_name=nm;}
    public void setIsFinished(Boolean isFinished){this.isFinished=isFinished;}
}
