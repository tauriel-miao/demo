package com.tauriel.demo.es_sql_demo;

import com.floragunn.searchguard.ssl.util.SSLConfigConstants;
import org.elasticsearch.common.settings.Settings;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class JdbcTest_SSL_Druid {

    String driver = "org.elasticsearch.jdbc.api.ElasticDriver";
    String elasticsearchAddress = "172.16.160.167:9300";
    private int index = 0;

    public Properties connectionProperties(){
        Properties properties = new Properties();
        properties.put("user", "admin");
        properties.put("password", "admin");
        return properties;
    }

    @Test
    public void test() throws UnsupportedEncodingException {
        try {
            String address = "jdbc:elastic://admin:admin@" + elasticsearchAddress;
            Properties properties = new Properties();
            properties.put("url", address);
            properties.put("username", "admin");
            properties.put("password", "admin");
            System.setProperty("es.set.netty.runtime.available.processors", "false");
            MyElasticSearchDruidDataSource dds = MyElasticSearchDruidDataSourceFactory.createDataSource(properties);
            dds.setInitialSize(1);
            Connection connection = dds.getConnection(getSettings());
            //dds = modifyDruidDataSource(dds, elasticsearchAddress);
            //connection = dds.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from test_es_sql where age > 15 and age < 18");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {

                System.out.println(resultSet.getString(0));
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getInt(3));
//                System.out.println(resultSet.getInt(4));
            }
            ps.close();
            connection.close();
            dds.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Settings getSettings() throws UnsupportedEncodingException {
        ClassLoader classLoader = JdbcTest_SSL_Druid.class.getClassLoader();
        URL resource = classLoader.getResource("kirk-keystore.jks");
        URL truresource = classLoader.getResource("truststore.jks");
        String keypath = URLDecoder.decode(resource.getPath(), "UTF-8");
        String trupath = URLDecoder.decode(truresource.getPath(), "UTF-8");

        //windows中路径会多个/ 如/E windows下需要打开注释
        if (keypath.startsWith("/")) {
            keypath=keypath.substring(1, keypath.length());
        }
        if (trupath.startsWith("/")) {
            trupath = trupath.substring(1, trupath.length());
        }

        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "searchguard_demo")
                .put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_ENABLED, true)
                .put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_KEYSTORE_FILEPATH, keypath)
                .put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_KEYSTORE_PASSWORD, "ks_password")
                .put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_TRUSTSTORE_FILEPATH, trupath)
                .put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_TRUSTSTORE_PASSWORD, "ts_password")
						.put(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_FILEPATH, keypath)
						.put(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_KEYSTORE_PASSWORD, "changeit")
						.put(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_TRUSTSTORE_FILEPATH, trupath)
						.put(SSLConfigConstants.SEARCHGUARD_SSL_HTTP_TRUSTSTORE_PASSWORD, "changeit")
						.put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_PEMKEY_PASSWORD, "admin")
                .put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_ENFORCE_HOSTNAME_VERIFICATION, false)
                //.put("client.transport.sniff", true)
                .build();// 集群名
        return settings;
    }


}
