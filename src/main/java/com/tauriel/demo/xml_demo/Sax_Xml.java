package com.tauriel.demo.xml_demo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname Sax_Xml
 * @Description TODO
 * @Date 2019/6/11 16:51
 * @Created by Tauriel
 */
public class Sax_Xml {


    public static void main(String[] args) throws Exception {
        //1.或去SAXParserFactory实例
        SAXParserFactory factory = SAXParserFactory.newInstance();
        //2.获取SAXparser实例
        SAXParser saxParser = factory.newSAXParser();
        //创建Handel对象
        SAXDemoHandel handel = new SAXDemoHandel();
        saxParser.parse("C:\\Users\\Bairong\\Downloads\\tauriel_test_project20190611155559.bak",handel);
        Map<String, Object> map = SAXDemoHandel.map;
        System.out.println(map);
    }
}

class SAXDemoHandel extends DefaultHandler {

    boolean isAppear = false;
    boolean isStop = false;
    String targetParam = null;
    String targetValue = null;

    static Map<String, Object> map = new HashMap<>();

    //遍历xml文件开始标签
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("sax解析开始");
    }

    //遍历xml文件结束标签
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("sax解析结束");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        String value = attributes.getValue("sv:name");
        if(!isStop){
            if(isAppear && qName.equals("sv:node")){
                isStop = true;
                return;
            }
            System.out.print("节点名称:"+ value +"----");
            if(targetParam != null && targetParam.equals("jcr:uuid") && qName.equals("sv:value")){
                targetValue = "";
            }
            if(value != null && value.equals("jcr:uuid")){
                targetParam = "jcr:uuid";
            }
        }
        if (qName.equals("sv:node") && !isAppear){
            isAppear = true;
            map.put("sv:name", value);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if(!isStop){
            super.characters(ch, start, length);
            String value = new String(ch,start,length).trim();
            if (!value.equals("")) {
                System.out.println(value);
            }
            if(targetValue != null){
                map.put(targetParam, value);
                targetValue = null;
                targetParam = null;
            }
        }
    }
}

