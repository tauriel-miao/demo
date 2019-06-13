package com.tauriel.demo.web_crawlers_demo.bean;

public class CommonBean {

    public static String getType(Object object){
        String typeName=object.getClass().getName();
        int length= typeName.lastIndexOf(".");
        String type =typeName.substring(length+1);
        return type;
    }


}
