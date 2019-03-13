package com.example.a12306_final;

import cn.bmob.v3.BmobObject;

/**
 * Created by 老汉 on 2017/11/30.
 */

public class Stations_usual extends BmobObject {
    private String station_name;

    public String getStation_name(){
        return station_name;
    }
    public void setStation_name(String station_name){
        this.station_name=station_name;
    }
}
