package com.tauriel.demo.web_crawlers_demo.business;

import com.tauriel.demo.util.HttpClientResult;
import com.tauriel.demo.util.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Music163_Ajax {

    private final static Logger log = LoggerFactory.getLogger(Music163_Ajax.class);

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

    public static String getWebPageByLable(Document doc){
        //获取到页面中的所有a标签
        Elements eles = doc.getElementsByTag("a");
        for(Element ele : eles){
            String title = ele.text(); //获取a标签的内容
            String aurl = ele.attr("href"); //获取a标签的属性
            log.debug(title+" - "+aurl);
            System.out.println("~~~~~~~~~~  title :  " + title + "  *********  url :  " + aurl);
        }
        return null;
    }


    public static Map<String, String> getAsrseaParams(){
        //asrsea
        System.setProperty("webdriver.chrome.driver", "D:\\soft\\Chrome\\chromedriver_win32\\chromedriver.exe");
        WebDriver  webDriver = new ChromeDriver();
        webDriver.manage().timeouts().setScriptTimeout(60,TimeUnit.SECONDS);
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)webDriver;
        String songId = "1361348080";
        webDriver.get("https://music.163.com/#/song?id=" + songId);

        String methodName = "asrsea('"+ MusicParam.getParam0_comment(songId) + "','" +  MusicParam.getParam1() + "','" + MusicParam.getParam2() + "','" + MusicParam.getParam3() + "')";
        Map<String, String> obj = (Map<String, String>) javascriptExecutor.executeScript("return window." + methodName);
        System.out.println("result : " + obj);

        webDriver.close();
        return obj;
    }

    public static Map<String, String> getHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "*/*");
        headers.put("accept-encoding", "gzip, deflate, br");
        headers.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
        headers.put("content-type", "application/x-www-form-urlencoded");
        headers.put("cookie", "_iuqxldmzr_=32; _ntes_nnid=5f35c81e1c3d3568081d4e63a6369dac,1556978311215; _ntes_nuid=5f35c81e1c3d3568081d4e63a6369dac; WM_TID=oOLDQhrplvBAFRUVQVdtiYzTiiPUW8Oh; JSESSIONID-WYYY=vAq2iywtKNzXtWFFDBYQDCrVwdc%2Bu2DA%2F5BI%2B5%5CRvfiQsHHsFIt4HAmpGo2xgric7VMyoE%5Ct1ZQnXkHgYEv0e%5C69VXpp5KyZMWtrp2%2FKTCyyZ%5CEZJ6Z7%2Fxw%5C%5CgSzbwpbJCiAjkFvcstSSpYJ%2BtWhvdxRofICnYlFQUCijlQ8ceRcHbAU%3A1559967429130; WM_NI=FVZ%2BzjoLxdBOIVVADUwwquwGVG5K4Z5RYcC5wZq0gJLqBx%2FLkTqnO%2B86QhBDmn5FwseMSb2w5zt3v5vCzvbvBt%2FmqLrMUz7DwP8LpyrbsQ9sTiDDWDWgRi%2BZHo9umWuienk%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6ee82f1408ab7bd89c97faf928ab6d44b869a8ababc67f4b1a6b5f662869bbc98f32af0fea7c3b92a868a96ccf962ad94b885c1458b8a9aa2c75fb5a9bf96f64d9699fbacb752f78e9784c23fb586bfd1e64290af8e99ae48a98cbf8fce21fcea9f89c150afb2fba7f679a7939d83b17df7a6bcd4f53fb69db697e17e82ace18ef448b2939b9bb650b590f8adf373b2bebdb2bc7998b78ca5d65095b99e8aaa528cb1f796f05db7b7ab8fea37e2a3");
        return headers;
    }

    public static void main(String[] args) throws Exception {
        String url = "";
        Map<String, String> asrseaParams = getAsrseaParams();
        HttpClientResult httpClientResult = HttpClientUtils.doPost(url, getHeaders(), asrseaParams);
        int code = httpClientResult.getCode();
        String content = httpClientResult.getContent();
        System.err.println("Result : code = " + code + " , content = " + content);

    }



}
