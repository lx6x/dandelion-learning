package org.dandelion.data.jdbc;

import org.dandelion.commons.utils.DateUtils;

import java.sql.*;

/**
 * TODO jdbc mysql 连接测试
 *
 * @author L
 * @version 1.0
 * @date 2021/10/12 11:42
 */
public class JdbcMysql {

    private static Connection connection;

    /**
     * 加载连接
     *
     * @author L
     *//*
    public static void loadConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("====> 数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://192.168.44.128:3306/nacos?characterEncoding=UTF-8",
                    "root",
                    "root"
            );
            System.out.println("====> 数据库连接成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/


    /**
     * 加载连接
     *
     * @author L
     */
    public static void loadConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("====> 数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://192.168.80.100:3306/flink_a?characterEncoding=UTF-8",
                    "root",
                    "root"
            );
            System.out.println("====> 数据库连接成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insert() {
        try {
            Statement statement = connection.createStatement();
            for (int i = 0; i < 100000; i++) {
                String nowDate = DateUtils.getNowDate(DateUtils.getNowDate());
//                String sql = "insert into account (name,balance,create_time,update_time) values(" + i + "," + i + "," + nowDate + "," + nowDate + ")";
                String sql = "insert into account (name,balance,create_time,update_time) values('" + i + "','" + i + "','" + nowDate + "','" + nowDate + "')";
                statement.executeUpdate(sql);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void tableList() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT TABLE_NAME FROM information_schema.`TABLES` WHERE TABLE_SCHEMA = 'flink_a';");
            while (resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
//                System.out.println(metaData.toString());
                int count = metaData.getColumnCount();
                for (int i = 1; i <= count; i++) {

                    Object object = resultSet.getObject(i);
                    System.out.println(String.valueOf(object));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        loadConnection();
        tableList();
    }
}
