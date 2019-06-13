package com.tauriel.demo.es_sql_demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.*;
import java.util.Properties;

public class JdbcTest_Kerberos_HikariCP {

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
    public void test() throws Exception {
        ClassLoader classLoader = JdbcTest_Kerberos_HikariCP.class.getClassLoader();
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

        esKerberosDruidDataSource(keypath, trupath);

        HikariDataSource ds = getHKariDataSource();
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try{
            //创建connection
            conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from test_es_sql ");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(0));
                System.out.println(resultSet.getInt(1));
            }
            //关闭connection
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }


    public HikariDataSource getHKariDataSource() throws Exception {
        String elasticsearchAddress = "172.16.150.161:9300";
        String address = "jdbc:elasticsearch://" + elasticsearchAddress;



        Properties properties = new Properties();
        properties.put("url", address);

        DruidDataSource dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
        dds.setInitialSize(1);

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(address);
        hikariConfig.setDataSource(dds);
/*        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");*/

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        return hikariDataSource;
    }

}
