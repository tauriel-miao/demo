package com.tauriel.demo.web_crawlers_demo.business;

/**
 * @Classname MusicUrl
 * @Description TODO
 * @Date 2019/6/13 14:44
 * @Created by Tauriel
 */
public class MusicUrl {

    public String getSongCommentUrl(String songId){
        String url = "https://music.163.com/api/v1/resource/comments/R_SO_4_" + songId + "?csrf_token=";
        return url;
    }

    public String getSongDetail(String songId){
        String url = "";
        return url;
    }

    public String getSongLyricUrl(String songId){
        String url = "";
        return url;
    }

    public String getUserRecentSongUrl(){
        String url = "https://music.163.com/weapi/v1/play/record?csrf_token=";
        return url;
    }

    public String getUserFollowsUrl(String userId){
        String url = "https://music.163.com/weapi/user/getfollows/" + userId + "?csrf_token=";
        return url;
    }

    public String getUserFansUrl(String userId){
        String url = " https://music.163.com/weapi/user/getfolloweds?csrf_token=";
        return url;
    }

    public String getUserEventUrl(String userId){
        String url = "https://music.163.com/weapi/event/get/" + userId +"?csrf_token=";
        return url;
    }
}
