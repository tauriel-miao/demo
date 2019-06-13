package com.tauriel.demo.lucence_demo.ik;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

public class IkAnalyzer6x extends Analyzer{

    private boolean useSmart;

    public boolean useSmart(){
        return useSmart;
    }

    public void setUseSmart(boolean useSmart){
        this.useSmart = useSmart;
    }

    public IkAnalyzer6x(){
        //IK分词器Lucene Analyzer接口实现类
        //默认细粒度切分算法
        this(false);
    }

    //IK分词器Lucene Analyzer接口实现类：当为true时，分词器进行智能切分
    public IkAnalyzer6x(boolean useSmart){
        super();
        this.useSmart = useSmart;
    }

    //重写最新版本的createComponents；重载Analyzer接口，构造分词组件
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer  _IKTokenizer = new IkTokenizer6x(this.useSmart());
        return new TokenStreamComponents(_IKTokenizer);
    }


}
