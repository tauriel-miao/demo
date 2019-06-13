package com.tauriel.demo.web_crawlers_demo.business;

import com.tauriel.demo.web_crawlers_demo.bean.PlayListAndUser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Music163SongList {

    private final static Logger log = LoggerFactory.getLogger(Music163SongList.class);

    public static Document getAllWebPage(String url){
        try{
            //doc代表一个页面
            Document doc = Jsoup.connect(url).get();
            //System.out.println(doc.body());
            return doc;
        } catch (Exception e){
            log.error(" 获取网页失败");
            return null;
        }
    }

    public static String getSongIdListByDoc(Document doc){
        List<String> urlList = new ArrayList<>();
        Elements songListPreCache = doc.getElementById("song-list-pre-cache").getElementsByTag("li");
        for (Element e : songListPreCache){
            String songUrl = e.getElementsByTag("a").attr("href");
            urlList.add(songUrl);
        }
        return urlList.toString();
    }

    public static String getSongListIdByDoc(Document doc){
        List<PlayListAndUser> list = new ArrayList<>();
        Elements elementsByClass = doc.getElementsByClass("m-rctlist");
        for(Element e : elementsByClass){
            Elements liEle = e.getElementsByTag("li");
            for (Element ele : liEle){
                Elements infoEle = ele.getElementsByClass("info");
                for(Element element : infoEle){
                    Elements pEle = element.getElementsByTag("p");
                    String songListId = pEle.get(0).getElementsByTag("a").attr("href");
                    String userId = pEle.get(1).getElementsByTag("a").attr("href");
                    PlayListAndUser playListAndUser = new PlayListAndUser();
                    playListAndUser.setPlayListUrl(songListId);
                    playListAndUser.setUserUrl(userId);
                    list.add(playListAndUser);
                }
            }
        }
        return list.toString();
    }
    
    public static void main(String[] args) {
        String url = "https://music.163.com/playlist?id=33580758";
        Document allWebPage = getAllWebPage(url);
        String songIdList = getSongIdListByDoc(allWebPage);
        System.out.println("songIdList : " + songIdList);
        String songListId = getSongListIdByDoc(allWebPage);
        System.out.println("songListId : " + songListId);
    }



}
