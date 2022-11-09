package org.dandelion.data.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO 官网：https://prestodb.io/docs/current/overview.html
 *
 * @author L
 * @version 1.0
 * @date 2021/12/8 9:38
 */
public class JdbcPresto {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("io.prestosql.jdbc.PrestoDriver");
        Connection connection = DriverManager.getConnection("jdbc:presto://192.168.80.100:10002/system/runtime", "root", "");
        Statement statement = connection.createStatement();
        ResultSet showTables = statement.executeQuery("select * from postgresql.public.t_label_group as pg right join mysql.presto_test.tb_user as my on pg.id=my.id");
        List<Map<String, Object>> list = new ArrayList<>();
        while (showTables.next()) {
            ResultSetMetaData metaData = showTables.getMetaData();
            int columnCount = metaData.getColumnCount();
            Map<String, Object> re = new LinkedHashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                String name = metaData.getColumnName(i);
                Object value = showTables.getObject(i);
                re.put(name, value);
            }
            list.add(re);
        }
        showTables.close();
        connection.close();

        System.out.println(list);
    }
}
