package com.tauriel.demo.tempObject;

import org.apache.commons.net.ntp.TimeStamp;

import java.sql.*;

public class tmpObj {

    final static String cfn = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    final static String url = "jdbc:sqlserver://localhost:1433;DatabaseName=test_1";

    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet res = null;
        try {
            Class.forName(cfn);
            con = DriverManager.getConnection(url,"sa","rootroot");
//SELECT B.VALUE AS NAME, A.counts AS COUNTS FROM (SELECT RESULT AS result, count(1) AS counts FROM CRD_DATA_DECISIONRESULT WHERE PRODUCT_CODE = ? AND CREATE_TIME>= ? AND CREATE_TIME <= ? GROUP BY RESULT) A LEFT JOIN CRD_STA_DICTIONARIES B ON A.RESULT = B.CODE
//Parameters: bj_bank20190425962975(String), 2019-04-25 15:43:39.297(Timestamp), 2019-04-25 15:46:11.973(Timestamp)
            String sql = "SELECT B.VALUE AS NAME, A.counts AS COUNTS FROM (SELECT RESULT AS result, count(1) AS counts FROM CRD_DATA_DECISIONRESULT WHERE PRODUCT_CODE = ? AND CREATE_TIME>= ? AND CREATE_TIME <= ? GROUP BY RESULT) A LEFT JOIN CRD_STA_DICTIONARIES B ON A.RESULT = B.CODE";
            statement = con.prepareStatement(sql);
            statement.setString(1,"bj_bank20190425962975");
            statement.setTimestamp(2, Timestamp.valueOf("2019-04-25 15:43:39.297"));
            statement.setTimestamp(3, Timestamp.valueOf("2019-04-25 15:46:11.973"));
            res = statement.executeQuery();
            while(res.next()){
                String title = res.getString("NAME");//获取test_name列的元素                                                                                                                                                    ;
                System.out.println("名字："+title);
                String codes = res.getString("COUNTS");//获取test_name列的元素                                                                                                                                                    ;
                System.out.println("数量："+ codes);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            try {
                if(res != null) res.close();
                if(statement != null) statement.close();
                if(con != null) con.close();
            } catch (Exception e2) {
                // TODO: handle exception
                e2.printStackTrace();
            }
        }
    }


}
