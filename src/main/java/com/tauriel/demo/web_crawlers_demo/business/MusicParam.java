package com.tauriel.demo.web_crawlers_demo.business;

public class MusicParam {

    public static String getParam0_getRecentList(String userId,int offset, int limit){
        String str = "{\"uid\":\"" + userId + "\",\"type\":\"-1\",\"limit\":\"" + limit + "\",\"offset\":\"" + offset + "\",\"total\":\"true\",\"csrf_token\":\"\"}";
        return str;
    }

    public static String getParam0_getEvent(String userId, int offset, int limit){
        String str = "{\"userId\":\"" + userId + "\",\"total\":\"true\",\"limit\":\"" + limit + "\",\"time\":\"-1\",\"getcounts\":\"true\",\"csrf_token\":\"\"}";
        return str;
    }

    public static String getParam0_getFans(String userId, int offset, int limit){
        String str = "{\"uid\":\"" + userId + "\",\"offset\":\"" + offset + "\",\"total\":\"true\",\"limit\":\"" + limit + "\",\"csrf_token\":\"\"}";
        return str;
    }

    public static String getParam0_getFollows(String userId, int offset, int limit){
        String str = "{\"uid\":\"" + userId + "\",\"offset\":\"" + offset + "\",\"total\":\"true\",\"limit\":\"" + limit + "\",\"csrf_token\":\"\"}";
        return str;
    }

    public static String getParam0_comment(String songId, int offset, int limit){
        String str = "{\"rid\":\"R_SO_4_" + songId + "\",\"offset\":\"" + offset + "\",\"total\":\"true\",\"limit\":\"" + limit + "\",\"csrf_token\":\"\"}";
        return str;
    }

    public static String getParam0_detail(String songId){
        String str = "";
        return str;
    }

    public static String getParam0_lyric(String songId){
        String str = "";
        return str;
    }

    public static String getParam1(){
        return "010001";
    }

    public static String getParam2(){
        String str = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";
        return str;
    }

    public static String getParam3(){
        String str = "0CoJUm6Qyw8W8jud";
        return str;
    }

}
