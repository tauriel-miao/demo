package com.tauriel.demo.es_sql_demo;

import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;

import java.util.Map;
import java.util.Properties;

public class MyElasticSearchDruidDataSourceFactory extends ElasticSearchDruidDataSourceFactory {


    public static MyElasticSearchDruidDataSource createDataSource(Properties properties) throws Exception {
        return createDataSource((Map) properties);
    }


    public static MyElasticSearchDruidDataSource createDataSource(Map properties) throws Exception {
        MyElasticSearchDruidDataSource dataSource = new MyElasticSearchDruidDataSource();
        config(dataSource, properties);
        return dataSource;
    }

}
