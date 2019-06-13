package com.tauriel.demo.web_crawlers_demo.util;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CallJsUtil {


    public static void callJsFile() throws ScriptException, NoSuchMethodException, IOException{

        String uri = "https://music.163.com/weapi/song/lyric?csrf_token=";
        String id = "1334295185";

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        String jsFileName = "D:\\workspace_idea\\tauriel-project-demo\\src\\main\\resources\\js\\core_5ff5ae10c7d6ef3aaf20a6232f2eb158.js";   // 读取js文件

        FileReader reader = new FileReader(jsFileName);   // 执行指定脚本
        Object eval = engine.eval(reader);

        if(engine instanceof Invocable) {
            Invocable invoke = (Invocable)engine;    // 调用merge方法，并传入两个参数

// c = merge(2, 3);

            String c = (String)invoke.invokeFunction("tauriel", Param_JsLogicTranJava.getFirstParamJson(id), "010001",
                    "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7",
                    "0CoJUn6Qyw8W8jud");

            System.out.println("c = " + c);
        }

        reader.close();
    }
}
