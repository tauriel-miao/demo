package com.tauriel.demo.web_crawlers_demo.business;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Music163UserInfo {

    private final static Logger log = LoggerFactory.getLogger(Music163UserInfo.class);

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

    public static String getUserRecentSongList(Document doc) {
        List<String> list = new ArrayList<>();
        Elements elements = doc.select(".g-bd .p-prf #more");
        for (Element e : elements){
            String href = e.attr("href");
            list.add(href);
        }
        return list.toString();
    }

    public static Elements getUserInfo(Document doc){
        Elements elements = doc.select(".g-bd .p-prf .m-proifo dd ul li");
        return elements;
    }

    public static String getUserEvent(Document doc){
        String userEvent = getUserInfo(doc).get(0).getElementsByTag("a").attr("href");
        return userEvent;
    }

    public static String getUserFans(Document doc){
        String userEvent = getUserInfo(doc).get(2).getElementsByTag("a").attr("href");
        return userEvent;

    }

    public static String getUserFollows(Document doc){
        String userEvent = getUserInfo(doc).get(1).getElementsByTag("a").attr("href");
        return userEvent;
    }

    public static void main(String[] args) {
        String url = "https://music.163.com/user/home?id=40234990";
        Document allWebPage = getAllWebPage(url);
        String userRecentSongList = getUserRecentSongList(allWebPage);
        System.out.println("userRecentSongList : " + userRecentSongList);
        String userEvent = getUserEvent(allWebPage);
        System.out.println("userEvent : " + userEvent);
        String userFollows = getUserFollows(allWebPage);
        System.out.println("userFollows : " + userFollows);
        String userFans = getUserFans(allWebPage);
        System.out.println("userFans : " + userFans);
    }



}
