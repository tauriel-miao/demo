/*package com.tauriel.demo.lucence_demo.index;


import com.tauriel.demo.lucence_demo.ik.IkAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class DeleteIndex {

    public static void main(String[] args) {

        //删除title中含有关键字“美国”的文档
        //若创建索引时使用ik构造时添加true参数，则会智能分词，使“美国总统”分为一个词，导致按“美国”删除时删除失败
        deleteDoc("title","美国");
    }

    private static void deleteDoc(String title, String str) {
        try {
            IkAnalyzer6x analyzer = new IkAnalyzer6x();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            Path indexdir = Paths.get("indexdir/testindex");
            Directory directory = FSDirectory.open(indexdir);

            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
            Term term = new Term(title, str);
            indexWriter.deleteDocuments(term);
            indexWriter.commit();
            indexWriter.close();
            System.out.println("success");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}*/
