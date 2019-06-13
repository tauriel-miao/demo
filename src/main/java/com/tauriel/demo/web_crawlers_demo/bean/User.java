package com.tauriel.demo.web_crawlers_demo.bean;

/**
 * @Classname User
 * @Description TODO
 * @Date 2019/6/13 16:44
 * @Created by Tauriel
 */
public class User {

    private String userName;
    private Integer userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
