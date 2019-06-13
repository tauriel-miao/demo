package com.tauriel.demo.web_crawlers_demo.util;

public class Param_JsLogicTranJava {

    public static String getFirstParamJson(String id){
        String str = "{\"id\":\"" + id + "\",\"lv\":-1,\"tv\":-1,\"csrf_token\":\"\"}";
        return str;
    }

    public static String getThirdParam(){
        String encSecKey = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";
        return encSecKey;
    }

    public static String getSecondParam(){
        return "010001";
    }

    public static String getFourthParam(){
        return "0CoJUn6Qyw8W8jud";
    }

    public static String aesEncrypt(){
        return null;
    }


    public static void setMaxDigits(){

    }
}
