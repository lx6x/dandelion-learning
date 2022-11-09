package org.dandelion.data.jdbc;

import org.dandelion.commons.utils.DateUtils;

import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/3/10 9:50
 */
public class JdbcPostgresql {

    private static Connection connection;

    /**
     * 加载连接
     *
     * @author L
     */
    public static void loadConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("====> 数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://192.168.80.100:5432/p",
                    "postgres",
                    "postgres"
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

    public static void select() throws SQLException {
        Statement statement = connection.createStatement();
        List<Map<String, String>> list = new LinkedList<>();
        ResultSet resultSet = statement.executeQuery("select * from flinkx_test_01");
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

        for (Map<String, String> map : list) {

            String datetime = map.get("datetime");

            Date parse = DateUtils.parse( datetime);
            System.out.println(datetime+" - "+parse);


//            System.out.println(map);
        }

    }

    public static void main(String[] args) throws SQLException {
        loadConnection();


        try {
            Statement statement = connection.createStatement();
            for (int i = 10; i <20; i++) {
                String nowDate = DateUtils.getNowDate(DateUtils.getNowDate());
//                String sql = "insert into account (name,balance,create_time,update_time) values(" + i + "," + i + "," + nowDate + "," + nowDate + ")";
                // INSERT INTO public.c_task_structure_result
                //(job_name, job_result, job_time, num_read, num_write, byte_read, byte_write, null_errors, n_errors, times)
                //VALUES('1510096788670967809', 1, '20987', '6', '0', '2640', '2784', '0', '0', '2022-04-02 11:30:36.242');
                String sql = "INSERT INTO public.t_task_un_structure_exe_del\n" +
                        "(id, task_un_exe_id, source_file_name, target_file_name, file_type, file_size, write_start_date, write_finish_time, structure_instruction, file_date, write_status)\n" +
                        "VALUES("+i+", '42452435234', 'test.txt', 'test.txt', 0, 0, '2022-04-13 11:00:00', '2022-04-13 11:00:00', '', '2022-04-13 11:00:00', 1);\n";
                statement.executeUpdate(sql);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
