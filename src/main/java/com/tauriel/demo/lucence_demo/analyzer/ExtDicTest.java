package com.tauriel.demo.lucence_demo.analyzer;

import com.tauriel.demo.lucence_demo.ik.IkAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

public class ExtDicTest {

    private static String str = "厉害了我的哥！中国环保部门(北京)即将发布治理北京雾霾的方法！";

    public static void main(String[] args) throws IOException {
        Analyzer analyzer = new IkAnalyzer6x(true);
        StringReader reader = new StringReader(str);
        TokenStream tokenStream = analyzer.tokenStream(str, reader);
        tokenStream.reset();
        CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
        System.out.println("results :");
        while (tokenStream.incrementToken()){
            System.out.println(attribute.toString() + "|");
        }
        System.out.println("\n");
        analyzer.close();
    }
}
