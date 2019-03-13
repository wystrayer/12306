package com.example.a12306_final.javabean;

import cn.bmob.v3.BmobUser;

/**
 * Created by Strayer on 2017/11/25.
 */

public class _User extends BmobUser {
    private String realname;
    private String userId;
    private String password_config;

    public String getRealname(){
        return this.realname;
    }
    public void setRealname(String realname){
        this.realname=realname;
    }
    public String getUserId(){
        return this.userId;
    }
    public void setUserId(String userId){
        this.userId=userId;
    }
    public String getPassword_config(){
        return this.password_config;
    }
    public void setPassword_config(String password_config){
        this.password_config=password_config;
    }
}
