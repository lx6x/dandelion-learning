package org.dandelion.data.jdbc;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * TODO jdbc hive 测试
 *
 * @author L
 * @version 1.0
 * @date 2021/10/14 11:17
 */
public class JdbcHive {

    private static final String DRIVER_CLASS_NAME = "org.apache.hive.jdbc.HiveDriver";
    private static final String URL = "jdbc:hive2://192.168.80.100:10000/default";
    private static final String USER_NAME = "hive";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static void loadConnection() {
        try {
            Class.forName(DRIVER_CLASS_NAME).newInstance();
            System.out.println("====> 数据库驱动加载成功");
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println("====> 数据库连接成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void select() {
        String sql = "select * from student";
        List<Map<String, String>> list = new LinkedList<>();
        try {
            PreparedStatement pstsm = connection.prepareStatement(sql);
            ResultSet resultSet = pstsm.executeQuery();
            while (resultSet.next()) {
                Map<String, String> re = new LinkedHashMap<>();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int count = metaData.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    String name = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    re.put(name, value == null ? null : value.toString());
                }
                list.add(re);
            }
            resultSet.close();
            pstsm.close();
            connection.close();
            System.err.println(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        loadConnection();
        select();
    }
}
