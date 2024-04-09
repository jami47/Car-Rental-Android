package com.example.carrental;

public class UserInfo {
    public String birthday,city,email,name,phone;

    public UserInfo() {
    }
    public UserInfo(String birthday, String email, String name) {
        this.birthday = birthday;
        this.email = email;
        this.name = name;
    }


    public UserInfo(String birthday, String city, String email, String name, String phone) {
        this.birthday = birthday;
        this.city = city;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}
