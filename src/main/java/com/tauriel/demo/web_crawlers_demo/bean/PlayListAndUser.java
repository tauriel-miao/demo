package com.tauriel.demo.web_crawlers_demo.bean;

/**
 * @Classname PlayListAndUser
 * @Description  歌单与用户的对应关系
 * @Date 2019/6/13 15:38
 * @Created by Tauriel
 */
public class PlayListAndUser {

    private String userUrl;
    private String playListUrl;

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getPlayListUrl() {
        return playListUrl;
    }

    public void setPlayListUrl(String playListUrl) {
        this.playListUrl = playListUrl;
    }
}
