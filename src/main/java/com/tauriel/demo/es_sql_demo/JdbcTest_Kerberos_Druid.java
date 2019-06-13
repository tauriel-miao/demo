package com.tauriel.demo.es_sql_demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class JdbcTest_Kerberos_Druid {

    //String JDBC_DRIVER = "org.elasticsearch.jdbc.api.ElasticDriver";
    String JDBC_DRIVER = "org.elasticsearch.xpack.sql.jdbc.EsDriver";
    String CONNECTION_URL = "jdbc:es://172.16.150.161:9300_sql;principal=es/node2@YOYOSYS.COM";
    String SQL_STRING = "select * from test_es_sql";
    private int index = 0;

    public Properties connectionProperties(){
        Properties properties = new Properties();
        properties.put("user", "admin");
        properties.put("password", "admin");
        return properties;
    }

/*
    @Test
    public void testEs(){

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(CONNECTION_URL);
            pstm = conn.prepareStatement(SQL_STRING);
            rs = pstm.executeQuery();
            while (rs.next()) {
                System.out.print("1:" + rs.getInt(1) + "\t");
                System.out.println();
            }
            rs.close();
            pstm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstm.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
*/

    public void esKerberosDruidDataSource(String keyPath,String path ) throws IOException {

/*            Configuration conf = new Configuration();
            conf.setBoolean("hadoop.security.authorization", true);
            conf.set("hadoop.security.authentication", "Kerberos");
            try {
                UserGroupInformation.setConfiguration(conf);
                UserGroupInformation.loginUserFromKeytab(user, path);
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        Configuration conf = new Configuration();
        conf.set("hadoop.security.authentication","kerberos");
        System.setProperty("java.security.krb5.conf", keyPath);
//    System.setProperty("java.security.auth.login.config","E:/jaas.conf");
        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation.loginUserFromKeytab("es/172.16.150.161@YOYOSYS.COM",path);
        UserGroupInformation loginUser = UserGroupInformation.getLoginUser();
        System.out.println(loginUser.toString());
    }


    @Test
    public void test() throws IOException {
        ClassLoader classLoader = JdbcTest_Kerberos_Druid.class.getClassLoader();
        URL resource = classLoader.getResource("krb5.conf");
        URL truresource = classLoader.getResource("krb5.keytab");
        String keypath = URLDecoder.decode(resource.getPath(), "UTF-8");
        String trupath = URLDecoder.decode(truresource.getPath(), "UTF-8");

        //windows中路径会多个/ 如/E windows下需要打开注释
        if (keypath.startsWith("/")) {
            keypath=keypath.substring(1, keypath.length());
        }
        if (trupath.startsWith("/")) {
            trupath = trupath.substring(1, trupath.length());
        }

        String elasticsearchAddress = "172.16.150.161:9300";
        String address = "jdbc:elasticsearch://" + elasticsearchAddress;
        Properties properties = new Properties();
        properties.put("url", address);
/*            properties.put("username", "admin");
            properties.put("password", "admin");*/

        esKerberosDruidDataSource(keypath, trupath);
        try {
            DruidDataSource dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
            dds.setInitialSize(1);
            Connection connection = dds.getConnection();
            //dds = modifyDruidDataSource(dds, elasticsearchAddress);
            //connection = dds.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from test_es_sql ");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(0));
                System.out.println(resultSet.getInt(1));
            }
            ps.close();
            connection.close();
            dds.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

/*
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(CONNECTION_URL);
            pstm = conn.prepareStatement(SQL_STRING);
            rs = pstm.executeQuery();
            while (rs.next()) {
                System.out.print("1:" + rs.getInt(1) + "\t");
                System.out.println();
            }
            rs.close();
            pstm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstm.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
*/

    }


}
