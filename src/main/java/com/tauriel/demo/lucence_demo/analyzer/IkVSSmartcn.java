/*package com.tauriel.demo.lucence_demo.analyzer;

import com.tauriel.demo.lucence_demo.ik.IkAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

public class IkVSSmartcn {

    private static String strl = "公路局正在治理解放大道路面积水问题。";
    private static String str2 = "IKAnalyzer 是一个开源的，基于 java 语言开发 的轻量级的中文分词工具包。";
    private static String str3 = "习近平会见美国总统奥巴马，学习国外经验";

    public static void main(String[] args) {
        Analyzer analyzer = null;
        System.out.println("句子一 ：" + strl);
        System.out.println("SmartChineseAnalyzer 分词结果：");
        analyzer = new SmartChineseAnalyzer();
        printAnalyzer(analyzer, strl);
        System.out.println("IKAnalyzer 分词结果：");
        analyzer = new IkAnalyzer6x(true);
        printAnalyzer(analyzer, strl);
        System.out.println("---------------------------");
        System.out.println("句子二：" + str2);
        System.out.println("SmartChineseAnalyzer 分词结果：");
        analyzer = new SmartChineseAnalyzer();
        printAnalyzer(analyzer, str2);
        System.out.println("IKAnalyzer 分词结果：");
        analyzer = new IkAnalyzer6x(true);
        printAnalyzer(analyzer, str3);
        analyzer.close();
    }

    public static void printAnalyzer(Analyzer analyzer, String str){
        try {
            StringReader reader = new StringReader(str);
            TokenStream toStream = analyzer.tokenStream(str, reader);
            toStream.reset();
            CharTermAttribute toAttribute = toStream.getAttribute(CharTermAttribute.class);
            while (toStream.incrementToken()) {
                System.out.println(toAttribute.toString() + "|");
            }
            System.out.println();
        } catch(IOException e){
            e.printStackTrace();
        }

    }
}*/
