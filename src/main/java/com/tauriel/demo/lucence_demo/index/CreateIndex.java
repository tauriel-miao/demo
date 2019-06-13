package com.tauriel.demo.lucence_demo.index;


import com.tauriel.demo.lucence_demo.ik.IkAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class CreateIndex {

    /*
     * 词条化 ： 通过analyzer分词
     * 词向量 ： 包含词与词在文档中出现的频率
     *
     * 文档是Lucene索引的基本单位，比文档更小的单位是字段，字段是文档的一部分。
     * 字段由3部分组成： 名称（name）， 类型（type）， 取值（value）
     * 取值(value)   -->   文本类型(字符串，字符流等)
     *              -->   二进制类型
     *              -->   数值类型
     *  类型(type)    -->     TextField  :  TextField 会把该字段的内容索引并词条化，但是不保存词向量。
     *               -->     StringField    :   Stringfield 只会对该字段的内容索引 ， 但是并不词条化，也不保存词向量。字符串的值会 被索引为一个单独的词工页。
     *               -->     IntPoint   :   intPoint 适合索引值为 int 类型的字段。 IntPoint 是为了快速过滤的，如果需要展示出来需 要另存一个字段.
     *               -->     LongPoint  :   用法和 IntPoint类似
     *               -->     FloatPoint   :     用法和 IntPoint类似
     *               -->     DoublePoint    :   用法和 IntPoint类似
     *               -->     SortedDocValuesField   :   存储值为文本内容的 DocValue 宇段， SortedDocValuesField 适合索引字段值为文本内容井 且需要按值进行排序的宇段。
     *               -->     SortedSetDocValuesField    :   存储多值域的 DocValues 字段， SortedSetDocValuesField 适合索引字段值为文本内容并且 需要按值进行分组、聚合等操作的字段。
     *               -->     NumericDocValuesField  :   存储单个数值类型的 DocValues 宇段，主要包括（ int, long, float, double） 。
     *               -->     SortedNumericValuesField   :   存储数值类型的有序数组列表的 DocValues 宇段。
     *               -->     StoreField     :   StoredFi巳Id 适合索引只需要保存字段值不进行其他操作的宇段。
     */


    public static void main(String[] args) {
        //创建 3 个 News 对象
        News newsl = new News();
        newsl.setId(1);
        newsl.setTitle("习近平会见美国总统奥巴马，学习国外经验");
        newsl.setContent("国家主席习近平 9 月 3 日在杭州西湖国宾馆会见前来出席二十国集团领导人杭州峰会的美国总统奥巴马..");
        newsl.setReply(672);

        News news2 =new News();
        news2.setId(2);
        news2.setTitle("北大迎 4380 名新生农村学生 700 多人近年最多");
        news2.setContent("昨天，北京大学迎来 4380 名来自全国各地及数十个同家的本科新生。其中，农村学生共 700 余名，为近年最多...");
        news2 . setReply(995);

        News news3 =new News();
        news3.setId(3);
        news3.setTitle("特朗普宣誓（Donald Trump）就任美国第 45 任总统");
        news3.setContent("当地时间 1 月 20 日，唐纳德·特朗普在美国国会宣誓就职，正式成为美国第 45 任总统。");
        news3.setReply(1872);

        //创建ik分词器
        Analyzer analyzer = new IkAnalyzer6x();

        /*
         * 用这个配置对象创建好IndexWriter对象后，再修改这个配置对象的配置信息不会对IndexWriter对象起作用。
         * 如要在indexWriter使用过程中修改它的配置信息，通过 indexWriter的getConfig()方法获得 LiveIndexWriterConfig 对象，在这个对象中可查看该IndexWriter使用的配置信息，可进行少量的配置修改（看它的setter方法）
         */

        //设置使用哪种分词器
        IndexWriterConfig icw = new IndexWriterConfig(analyzer);
        //OpenMode.CREATE : 先清空索引再重新创建
        //OpenMode.APPEND : 附加索引
        //OpenMode.CREATE_OR_APPEND : 如果索引不存在则新建，若已存在则附加
        icw.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        //用于表示索引位置
        Directory dir = null;
        IndexWriter inWriter = null;
        //索引目录
        Path indexPath = Paths.get("indexdir/testindex");
        //开始时间
        Date start = new Date();

        try {
            if(!Files.isReadable(indexPath)){
                System.out.println("Document directory [ " + indexPath.toAbsolutePath() + " ] does not exist or is not readable, Please check the path");
                System.exit(1);
            }
            //索引保存路径
            dir = FSDirectory.open(indexPath);
            inWriter = new IndexWriter(dir, icw);


            /* 反向索引中，
             * IndexOptions.DOCS    :   只索引文档，词项频率和位移信息不保存
             * IndexOptions.DOCS_AND_FREQS  :   只索引文档和词项频率，位移信息不保存
             * IndexOptions.DOCS_AND_FREQS_AND_POSITIONS    :   索引文档、词项频率和位移信息。
             * IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_OFFSETS    :   索引文档、词项频率、位移信息和偏移量
             * IndexOptions.NONE    :   不索引
             *
             */

            //设置新闻ID索引并存储
            FieldType idType = new FieldType();
            idType.setIndexOptions(IndexOptions.DOCS);
            idType.setStored(true);

            //设置新闻标题索引文档、词项频率、位移信息和偏移量，存储并词条化
            FieldType titleType = new FieldType();
            titleType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
            titleType.setStored(true);
            titleType.setTokenized(true);

            FieldType contentType = new FieldType();
            contentType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
            //是否存储
            contentType.setStored(true);
            //是否分词
            contentType.setTokenized(true);
            //是否存储词项向量
            contentType.setStoreTermVectors(true);
            //词项向量中是否存储位置
            contentType.setStoreTermVectorPositions(true);
            //词项向量中是否存储偏移量
            contentType.setStoreTermVectorOffsets(true);
            //词项向量中是否存储附加信息
            contentType.setStoreTermVectorPayloads(true);

            //为文档添加数据
            Document doc1 = new Document();
            //为字段设置  字段名称，字段值，字段类型
            doc1.add(new Field("id", String.valueOf(newsl.getId()), idType));
            doc1.add(new Field("title", newsl.getTitle(), titleType));
            doc1.add(new Field("content", newsl.getContent(), contentType));
            doc1.add(new IntPoint("reply", newsl.getReply()));
            doc1.add(new StoredField("reply_display", newsl.getReply()));

            Document doc2 = new Document();
            doc2.add(new Field("id", String.valueOf(news2.getId()), idType));
            doc2.add(new Field("title", news2.getTitle(), titleType));
            doc2.add(new Field("content", news2.getContent(), contentType));
            doc2.add(new IntPoint("reply", news2.getReply()));
            doc2.add(new StoredField("reply_display", news2.getReply()));

            Document doc3 = new Document();
            doc3.add(new Field("id", String.valueOf(news3.getId()), idType));
            doc3.add(new Field("title", news3.getTitle(), titleType));
            doc3.add(new Field("content", news3.getContent(), contentType));
            doc3.add(new IntPoint("reply", news3.getReply()));
            doc3.add(new StoredField("reply_display", news3.getReply()));

            //为索引文件添加文档
            inWriter.addDocument(doc1);
            inWriter.addDocument(doc2);
            inWriter.addDocument(doc3);
            //生成索引
            inWriter.commit();
            inWriter.close();

        }catch(IOException e){
            e.printStackTrace();
        }

        Date end = new Date();
        System.out.println("索引文档用时：" + (end.getTime() - start.getTime()) + "milliseconds");
    }


}
