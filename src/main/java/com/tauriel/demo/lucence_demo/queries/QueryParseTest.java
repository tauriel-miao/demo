/*package com.tauriel.demo.lucence_demo.queries;


import com.tauriel.demo.lucence_demo.ik.IkAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QueryParseTest {

    @Test
    public void simpleTest() {
        try {
            String field = "title";
            Path indexPath = Paths.get("indexdir/testindex");
            Directory dir = FSDirectory.open(indexPath);
            DirectoryReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);

            Analyzer analyzer = new IkAnalyzer6x();
            QueryParser parser = new QueryParser(field, analyzer);
            parser.setDefaultOperator(QueryParser.Operator.AND);
            Query query = parser.parse("农村学生");     //查询关键词
            System.out.println("Query :" + query.toString());

            //返回前10条
            TopDocs tds = searcher.search(query, 10);
            for (ScoreDoc sd:tds.scoreDocs) {
                Document doc = searcher.doc(sd.doc);
                System.out.println("DocID :" + sd.doc);
                System.out.println("id:" + doc.get("id"));
                System.out.println("title:" + doc.get("title"));
                System.out.println("文档评分 ： " + sd.score);
            }

            dir.close();
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParseException e1){
            e1.printStackTrace();
        }


    }

    *//**
     *  词项搜索
     *//*
    @Test
    public void termQueryTest() {
        try {
            String fields = "title";

            Path indexPath = Paths.get("indexdir/testindex");
            Directory dir = FSDirectory.open(indexPath);
            DirectoryReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);

            Analyzer analyzer = new IkAnalyzer6x();
            Query query = new TermQuery(new Term("title", "美国"));
            System.out.println("Query :" + query.toString());

            //返回前10条
            TopDocs tds = searcher.search(query, 10);
            for (ScoreDoc sd:tds.scoreDocs) {
                Document doc = searcher.doc(sd.doc);
                System.out.println("DocID :" + sd.doc);
                System.out.println("id:" + doc.get("id"));
                System.out.println("title:" + doc.get("title"));
                System.out.println("文档评分 ： " + sd.score);
            }

            dir.close();
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    *//**
     *  布尔搜索
     *//*
    @Test
    public void booleanQueryTest() {
        try {
            Path indexdir = Paths.get("indexdir/testindex");
            Directory directory = FSDirectory.open(indexdir);
            DirectoryReader directoryReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(directoryReader);

            TermQuery query1 = new TermQuery(new Term("title", "美国"));
            TermQuery query2 = new TermQuery(new Term("content", "习近平"));
            BooleanClause booleanClause1 = new BooleanClause(query1, BooleanClause.Occur.MUST);
            BooleanClause booleanClause2 = new BooleanClause(query2, BooleanClause.Occur.MUST_NOT);
            BooleanQuery booleanQuery = new BooleanQuery.Builder().add(booleanClause1).add(booleanClause2).build();

            TopDocs docs = searcher.search(booleanQuery, 10);
            for (ScoreDoc scoreDoc : docs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                System.out.println("id:" + doc.get("id"));
                System.out.println("title:" + doc.get("title"));
                System.out.println("content:" + doc.get("content"));
                System.out.println("文档评分:" + scoreDoc.score);
                System.out.println("shardIndex:" + scoreDoc.shardIndex);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    *//**
     *  范围搜索
     *//*
    @Test
    public void rangeQueryTest() {
        try {
            Path indexdir = Paths.get("indexdir/testindex");
            Directory directory = FSDirectory.open(indexdir);
            DirectoryReader directoryReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(directoryReader);

            Query query = IntPoint.newRangeQuery("reply", 500, 1000);

            TopDocs docs = searcher.search(query, 10);
            for (ScoreDoc scoreDoc : docs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                System.out.println("id:" + doc.get("id"));
                System.out.println("title:" + doc.get("title"));
                System.out.println("content:" + doc.get("content"));
                System.out.println("文档评分:" + scoreDoc.score);
                System.out.println("shardIndex:" + scoreDoc.shardIndex);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    *//**
     *  前缀搜索
     *//*
    @Test
    public void prefixQueryTest() {
        try {
            Path indexdir = Paths.get("indexdir/testindex");
            Directory directory = FSDirectory.open(indexdir);
            DirectoryReader directoryReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(directoryReader);

            PrefixQuery query = new PrefixQuery(new Term("title", "奥"));

            TopDocs docs = searcher.search(query, 10);
            for (ScoreDoc scoreDoc : docs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                System.out.println("id:" + doc.get("id"));
                System.out.println("title:" + doc.get("title"));
                System.out.println("content:" + doc.get("content"));
                System.out.println("文档评分:" + scoreDoc.score);
                System.out.println("shardIndex:" + scoreDoc.shardIndex);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    *//**
     *  多关键字搜索  --  可以添加多个关键字，这些关键字之间yaome是紧密相连成为一个短语，要么在这几个关键字之间还插有其他无关的内容
     *  add添加了关键字后，可通过setSlop()方法来设定一个称之为“坡度”的变量来确定关键字之间是否允许或允许多少个词汇的存在。
     *//*
    @Test
    public void phraseQueryTest() {
        try {
            Path indexdir = Paths.get("indexdir/testindex");
            Directory directory = FSDirectory.open(indexdir);
            DirectoryReader directoryReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(directoryReader);

            PhraseQuery.Builder builder = new PhraseQuery.Builder();
            builder.add(new Term("title", "日本"), 2);
            builder.add(new Term("title", "美国"), 3);
            PhraseQuery query = builder.build();
            System.out.println("Query : " + query);

            TopDocs docs = searcher.search(query, 10);
            for (ScoreDoc scoreDoc : docs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                System.out.println("id:" + doc.get("id"));
                System.out.println("title:" + doc.get("title"));
                System.out.println("content:" + doc.get("content"));
                System.out.println("文档评分:" + scoreDoc.score);
                System.out.println("shardIndex:" + scoreDoc.shardIndex);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    *//**
     *  模糊搜索
     *//*
    @Test
    public void fuzzyQueryTest() {
        try {
            Path indexdir = Paths.get("indexdir/testindex");
            Directory directory = FSDirectory.open(indexdir);
            DirectoryReader directoryReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(directoryReader);

            FuzzyQuery query = new FuzzyQuery(new Term("title", "tramp"));

            TopDocs docs = searcher.search(query, 10);
            for (ScoreDoc scoreDoc : docs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                System.out.println("id:" + doc.get("id"));
                System.out.println("title:" + doc.get("title"));
                System.out.println("content:" + doc.get("content"));
                System.out.println("文档评分:" + scoreDoc.score);
                System.out.println("shardIndex:" + scoreDoc.shardIndex);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    *//**
     *  通匹配符搜索
     *//*
    @Test
    public void wildcardQueryTest() {
        try {
            Path indexdir = Paths.get("indexdir/testindex");
            Directory directory = FSDirectory.open(indexdir);
            DirectoryReader directoryReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(directoryReader);

            WildcardQuery query = new WildcardQuery(new Term("title", "学?"));

            TopDocs docs = searcher.search(query, 10);
            for (ScoreDoc scoreDoc : docs.scoreDocs) {
                Document doc = searcher.doc(scoreDoc.doc);
                System.out.println("id:" + doc.get("id"));
                System.out.println("title:" + doc.get("title"));
                System.out.println("content:" + doc.get("content"));
                System.out.println("文档评分:" + scoreDoc.score);
                System.out.println("shardIndex:" + scoreDoc.shardIndex);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    *//**
     *  高亮
     *//*
    @Test
    public void highlighterTest() {
        try {
            String field = "title";
            Directory directory = FSDirectory.open(Paths.get("indexdir/testindex"));
            DirectoryReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);

            Analyzer analyzer = new IkAnalyzer6x();
            QueryParser parser = new QueryParser(field, analyzer);
            Query query = parser.parse("北大");
            System.out.println("Query : " + query);
            QueryScorer scorer = new QueryScorer(query, field);
            //定制高亮标签
            SimpleHTMLFormatter fors = new SimpleHTMLFormatter("<span style=\"color:red: \">", "</span>");
            Highlighter highlighter = new Highlighter(fors, scorer);
            //高亮分词器
            TopDocs tds = searcher.search(query, 10);
            for(ScoreDoc sd : tds.scoreDocs){
                Document doc = searcher.doc(sd.doc);
                System.out.println("id : " + doc.get("id"));
                System.out.println("title : " + doc.get("title"));
                TokenStream tokenStream = TokenSources.getAnyTokenStream(searcher.getIndexReader(), sd.doc, field, analyzer);
                //获取tokenStream
                SimpleSpanFragmenter fragment = new SimpleSpanFragmenter(scorer);
                highlighter.setTextFragmenter(fragment);
                String str= highlighter.getBestFragment(tokenStream, doc.get(field));
                System.out.println("highter : " + str);
            }
            directory.close();
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}*/
