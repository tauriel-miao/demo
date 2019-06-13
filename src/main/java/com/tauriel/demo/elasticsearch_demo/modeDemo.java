package com.tauriel.demo.elasticsearch_demo;

import java.util.*;

public class modeDemo {

    /**
     * 用于计算两个向量的夹角(ES中用这种余弦相似性理论来比较哪个文档与查询关键词相关度更大)
     * @param v1
     * @param v2
     * @return   cosA
     */
    public static double calCossSim(Map<String, Double> v1, Map<String, Double> v2){
        double sclar = 0.0, norm1 = 0.0, norm2 = 0.0, similarity = 0.0;
        Set<String> v1Keys = v1.keySet();
        Set<String> v2Keys = v2.keySet();
        Set<String> both = new HashSet<>();
        both.addAll(v1Keys);
        //两个set的交集
        both.retainAll(v2Keys);
        //[Hello,css]
        System.out.println(both);
        for (String str1: both) {
            //x * y 求和
            sclar += v1.get(str1) * v2.get(str1);
        }
        for(String str1: v1.keySet()){
            // x的平方 求和
            norm1 += Math.pow(v1.get(str1), 2);
        }
        for (String str2: v2.keySet()){
            // y的平方  求和
            norm2 += Math.pow(v2.get(str2), 2);
        }

        similarity = sclar/Math.sqrt(norm1 * norm2);
        System.out.println("sclar:" + sclar);
        System.out.println("norm1:" + norm1);
        System.out.println("norm2:" + norm2);
        System.out.println("similarity:" + similarity);
        return similarity;
    }


    public static void main(String[] args) {

        /**

         Map<String, Double> m1 = new HashMap<>();
         m1.put("Hello", 1.0);
         m1.put("css", 2.0);
         m1.put("Lucene", 3.0);
         Map<String, Double> m2 = new HashMap<>();
         m2.put("Hello", 1.0);
         m2.put("World", 2.0);
         m2.put("Hadoop", 3.0);
         m2.put("java", 4.0);
         m2.put("html", 1.0);
         m2.put("css", 2.0);
         //calCossSim(m1, m2);

         Map<String, Double> query = new HashMap<>();
         query.put("Hello", 1.0);
         query.put("css", 2.0);
         query.put("Lucene", 3.0);
         query.put("java", 4.0);
         calCossSim(m1, query);
         calCossSim(m2, query);

         **/

        List<String> doc1 = Arrays.asList("人工", "智能", "成为", "互联网", "大会", "焦点");
        List<String> doc2 = Arrays.asList("谷歌", "推出", "开源", "人工", "智能", "系统", "工具");
        List<String> doc3 = Arrays.asList("互联网", "的", "未来", "在", "人工", "智能");
        List<String> doc4 = Arrays.asList("谷歌", "开源", "机器", "学习", "工具");
        List<List<String>> documents = Arrays.asList(doc1,doc2,doc3,doc4);
        System.out.println(tf(doc2, "谷歌"));
        System.out.println(df(documents, "谷歌"));
        System.out.println(tfIdf(doc2, documents, "谷歌"));



    }

    /**
     * 计算  tf词频 （某个词在文档中出现的次数/文档的总词数）
     * @param doc
     * @param term
     * @return
     */
    public static double tf(List<String> doc, String term){
        double termFrequency = 0;
        for(String str : doc){
            if(term.equals(str)){
                termFrequency ++;
            }
        }
        return termFrequency / doc.size();
    }

    /**
     * 计算  df文档频率  (文档集中包含某个词的所有文档数目)
     * @param docs
     * @param term
     * @return
     */
    public static int df(List<List<String>> docs, String term){
        int n = 0;
        if(term != null && term != ""){
            for(List<String> doc : docs){
                for(String str : doc){
                    if (term.equalsIgnoreCase(str)) {
                        n ++;
                        break;
                    }
                }
            }
        }else{
            System.out.println("term 不能为 null 或者 空");
        }
        return n;
    }

    /**
     * 计算  逆文档频率  idf = log(文档集总的文档数 / (包含某个词的文档数 + 1))
     * 分母越大，说明这个词越常见，逆文档频率越小。
     * 分母中文档数 + 1 是进行平滑处理，防止出现分母为0的情况。
     * @param docs
     * @param term
     * @return
     */
    public static double idf(List<List<String>> docs, String term){
        return Math.log(docs.size() / (double) df(docs, term) + 1);
    }

    /**
     * 词项的权重用 TF-IDF 来表示。 tf-idf = tf * idf
     * @param doc
     * @param docs
     * @param term
     * @return
     */
    public static double tfIdf(List<String> doc, List<List<String>> docs, String term) {
        return tf(doc, term) * idf(docs, term);
    }


}
