package com.tauriel.demo.lucence_demo.index;


import com.tauriel.demo.lucence_demo.ik.IkAnalyzer6x;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * 更新原理不了解, 测试情况偶尔更新文档后不能够重新添加
 */

public class UpdateIndex {

    public static void main(String[] args) {
        //索引更新操作实质上是先删除索引，再重新建立新的文档
        updateDoc("title","美国");
    }

    private static void updateDoc(String title, String str) {
        try {
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new IkAnalyzer6x());
            Directory directory = FSDirectory.open(Paths.get("indexdir/testindex"));
            IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

            Document document = new Document();
            document.add(new TextField("title", "北京大学开学迎来了10000名新生", Field.Store.YES));
            document.add(new TextField("id", "2", Field.Store.YES));
            document.add(new TextField("content", "昨天，北京大学迎来了10000名来自全国各地及数十个国家的本科新生，到达近些来的最高峰值", Field.Store.YES));

            indexWriter.updateDocument(new Term("title", "习近平"), document);
            indexWriter.commit();
            indexWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
