package com.tauriel.demo.lucence_demo.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

public class VariousAnalyzers {

    private static String strCh = "中华人民共和国简称中国，是一个有13亿人口的国家";
    
    private static String strEn = "Dogs can not achieve a place, eyes can reach";

    public static void main(String[] args) {
        Analyzer analyzer = null;
        analyzer = new StandardAnalyzer();
        System.out.println("标准分词 ：" + analyzer.getClass());
        printAnalyzer(analyzer);
        analyzer = new WhitespaceAnalyzer();
        System.out.println("空格分词 ：" + analyzer.getClass());
        printAnalyzer(analyzer);
        analyzer = new SimpleAnalyzer();
        System.out.println("简单分词 ：" + analyzer.getClass());
        printAnalyzer(analyzer);
        analyzer = new CJKAnalyzer();
        System.out.println("二分法分词 ：" + analyzer.getClass());
        printAnalyzer(analyzer);
        analyzer = new KeywordAnalyzer();
        System.out.println("关键字分词 ：" + analyzer.getClass());
        printAnalyzer(analyzer);
        analyzer = new StopAnalyzer();
        System.out.println("停用词分词 ：" + analyzer.getClass());
        printAnalyzer(analyzer);
        analyzer = new SmartChineseAnalyzer();
        System.out.println("中文智能分词 ：" + analyzer.getClass());
        printAnalyzer(analyzer);
    }

    private static void printAnalyzer(Analyzer analyzer) {

        try {
           StringReader reader = new StringReader(strCh);
           TokenStream toStream = analyzer.tokenStream(strCh, reader);
           toStream.reset();
           CharTermAttribute toAttribute = toStream.getAttribute(CharTermAttribute.class);
           System.out.println("分词结果 ： ");
           while(toStream.incrementToken()){
               System.out.println(toAttribute.toString() + " | ");
           }
           System.out.println("\n");
           analyzer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
