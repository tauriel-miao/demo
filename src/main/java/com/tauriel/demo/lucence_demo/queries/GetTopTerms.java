/*
package com.tauriel.demo.lucence_demo.queries;


import com.tauriel.demo.lucence_demo.ik.IkAnalyzer6x;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GetTopTerms {

    public static final String index = "indexdir/getTopTermIndex";

  @Test
  public void indexDocs(){
      try{

          Directory directory = FSDirectory.open(Paths.get(index));
          IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new IkAnalyzer6x());
          IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

          Document doc = new Document();
          FieldType fieldType = new FieldType();
          fieldType.setStored(true);
          fieldType.setTokenized(true);
          fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);
          fieldType.setStoreTermVectors(true);

          File file = new File("files/ai.txt");
          String text = textToString(file);

          doc.add(new Field("content", text, fieldType));
          indexWriter.addDocument(doc);
          indexWriter.commit();
          indexWriter.close();
          directory.close();
      } catch (Exception e){
          e.printStackTrace();
      }

  }

    private String textToString(File file) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str = null;
        while((str = br.readLine()) != null){
            result.append(System.lineSeparator() + str);
        }
        br.close();
        return result.toString();
    }


    @Test
    public void getTopTermIndex(){
      try{

          IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
          Terms terms = reader.getTermVector(0, "content");
          TermsEnum termsEnum = terms.iterator();
          HashMap<String, Integer> map = new HashMap<>();
          BytesRef thisTerm;
          while((thisTerm = termsEnum.next()) != null){
              String termText = thisTerm.utf8ToString();
              map.put(termText, (int)termsEnum.totalTermFreq());
          }
          List<Map.Entry<String, Integer>> sortedMap = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
          Collections.sort(sortedMap, new Comparator<Map.Entry<String, Integer>>() {
              @Override
              public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                  return (o2.getValue() - o1.getValue());
              }
          });
          getTopN(sortedMap, 30);
      }catch (Exception e){
          e.printStackTrace();
      }
    }

    private void getTopN(List<Map.Entry<String, Integer>> sortedMap, int N) {
        for (int i = 0; i < N; i++){
            System.out.println(sortedMap.get(i).getKey() + " : " + sortedMap.get(i).getValue());
        }
    }

}

*/

