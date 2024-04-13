package com.example.tsgbackend.system.bean.utils;

public class RegisterForm {
    private String user_name;
    private String user_pwd;
    private String phone;

    public String getUser_name() {
        return user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
