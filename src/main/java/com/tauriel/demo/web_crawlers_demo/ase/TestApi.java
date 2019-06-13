package com.tauriel.demo.web_crawlers_demo.ase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TestApi {
    private static final Logger LOG = LoggerFactory.getLogger(TestApi.class);

    private static final String userId = "1334295185";

    private static Map<String, String> headers = new HashMap<>();

    /**
       {"rid":"R_SO_4_1334295185","offset":"0","total":"true","limit":"20","csrf_token":""}
       010001
       00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7
       0CoJUm6Qyw8W8jud


       {"id":"1334295185","lv":-1,"tv":-1,"csrf_token":""}
       {"logs":"[{\"action\":\"activeweb\",\"json\":{\"is_organic\":0,\"url\":\"https://music.163.com/#/song?id=1334295185\",\"source\":\"https://www.baidu.com/link?url=lknnHQ0cVZI26vhl0a3TXXY-6CpBZB5iI5NVYZQVL9a&ck=2421.2.78.167.147.198.147.423&shh=www.baidu.com&sht=baidu&wd=&eqid=9cd6efeb000c873a000000025c3d6ae3\"}}]","csrf_token":""}
       {"logs":"[{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358210052,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358180462,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358226755,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358225796,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358210030,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358207070,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358219874,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358212933,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358236521,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358197125,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358179410,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358231581,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358190195,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358191194,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358208962,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358210905,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358204010,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358188221,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358212810,\"sourceid\":\"1334295185\"}},{\"action\":\"commentimpress\",\"json\":{\"type\":\"song\",\"cid\":1358226569,\"sourceid\":\"1334295185\"}}]","csrf_token":""}
     */


    static {
        headers.put("Referer", "http://music.163.com");
        headers.put("Origin", "http://music.163.com");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
    }

    @Test
    public void testPlayList() {
        String data = "{\"uid\":" + userId + ",\"wordwrap\":\"7\",\"offset\":\"0\",\"total\":\"true\",\"limit\":\"1000\"}";
        String data1 = "{\"rid\":\"R_SO_4_1334295185\",\"offset\":\"0\",\"total\":\"true\",\"limit\":\"20\",\"csrf_token\":\"\"}";
        Map<String, String> forms = EncryptUtils.encrypt(data1);
/*        String url = "http://music.163.com/weapi/user/playlist";
        String text = Requests.post(url).headers(headers).forms(forms).send().readToText();
        LOG.debug(text);
        PlayListResponse playListResponse = JSON.parseObject(text, PlayListResponse.class);
        LOG.info(text);*/

    }

    @Test
    public void testRecord() {
        String data = "{\"uid\":" + userId + ",\"limit\":1000,\"offset\":0,\"total\":true,\"type\":\"-1\"}";

        Map<String, String> forms = EncryptUtils.encrypt(data);
/*        String url = "http://music.163.com/weapi/v1/play/record";
        String text = Requests.post(url).headers(headers).forms(forms).send().readToText();
        LOG.debug(text);
        RecordResponse recordResponse = JSON.parseObject(text, RecordResponse.class);
        LOG.info(text);*/
    }

    @Test
    public void testCloudSearch() {
        //"{"hlpretag":"<span class=\"s-fc7\">","hlposttag":"</span>","id":"32689809","s":"junbaor","type":"1002","offset":"0","total":"true","limit":"30","csrf_token":"5d2055cc7991877c678dbf55e61e8444"}"
        // type 1000 歌单 1002 用户 1009 电台
        String data = "{\"hlpretag\":\"\",\"hlposttag\":\"\",\"s\":\"junbaor\",\"type\":\"1002\",\"offset\":\"0\",\"total\":\"true\",\"limit\":\"30\"}";

        Map<String, String> forms = EncryptUtils.encrypt(data);
/*        String url = "http://music.163.com/weapi/cloudsearch/get/web";
        String text = Requests.post(url).headers(headers).forms(forms).send().readToText();
        LOG.debug(text);*/
    }
}
