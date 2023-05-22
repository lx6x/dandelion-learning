package org.dandelion.commons.utils;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/1/20 10:28
 */
public class FileUtil {

    public static boolean isValidFileName(String fileName) {
        if (fileName == null || fileName.length() > 255) {
            return false;
        } else {
            return fileName.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
        }
    }

    public static void getFiles(String path) throws IOException {
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            String s = tempList[i].toString();
            if (tempList[i].isFile()) {
                //文件名，不包含路径
                String fileName = tempList[i].getName();
                if (fileName.contains(".java")) {

                    System.out.println("      ");
                    System.out.println(fileName);
                    System.out.println("      ");

                    FileReader fr = new FileReader(tempList[i]);
                    BufferedReader br = new BufferedReader(fr);
                    String line;
                    while ((line = br.readLine()) != null) {
                        //process the line
                        System.out.println(line);
                    }
                    //close resources
                    br.close();
                    fr.close();
                }
            }
            if (tempList[i].isDirectory()) {
                getFiles(s);
            }
        }
    }

    /**
     * Use Streams when you are dealing with raw data
     *
     * @param data
     */
    public static void line(List<String> str, String path) {
        File record = new File(path);//记录结果文件
        try {
            if (!record.exists()) {

                File dir = new File(record.getParent());
                dir.mkdirs();
                record.createNewFile();
            }
            FileWriter writer = null;
            try {
                // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                writer = new FileWriter(record, true);
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
                for (String s : str) {
                    writer.write(s + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("记录保存失败");
        }
    }


    public static List<String> excel() {

        StringBuilder sb = new StringBuilder();

        sb.append("(");
        //创建File 读取文件
        File inputfile = new File("C:\\Users\\...\\Desktop\\正式流量数据\\operator_card_flow_compare_97044363419649.csv");
        try {
            FileInputStream inputStream = new FileInputStream(inputfile);
            ExcelReader reader = ExcelUtil.getReader(inputStream, 0);
            List<Map<String, Object>> mapList = reader.readAll();
            int size = mapList.size();
            int i = 1;
            for (Map<String, Object> map : mapList) {

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        sb.append(")");
//        System.out.println(sb);
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        List<String> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM lao_user WHERE ICCID IN " + sb + ";";
//            System.out.println(sql);
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String iccid = resultSet.getString("ICCID");
                String month = "2022-12";
                String _col3 = "1000000";
                String operatorsId = "49";

                String s = userId + "|" + iccid + "|" + month + "|" + _col3 + "|" + operatorsId;
//                System.out.println(s);
                list.add(s);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;

    }

    private static Connection connection;

    public static void dongDeConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("====> 数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://cmpuat.cluster-cft3pld5ezyf.rds.cn-northwest-1.amazonaws.com.cn/cmp_user?allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&rewriteBatchedStatements=true&failOverReadOnly=true&useUnicode=true&amp&characterEncoding=UTF-8",
                    "cmpnew",
                    "cmpnew@123"
            );
            System.out.println("====> 数据库连接成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getFile() throws IOException {
        String path = "C:\\Users\\...\\Desktop\\正式流量数据";
        File file = new File(path);
        File[] files = file.listFiles();
        for (File file1 : files) {
            String name = file1.getName();
            String s = path + "\\" + name;
            System.err.println(s);

            InputStream inputStream = new FileInputStream(file1);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            br.readLine();
            while (null != (line = br.readLine())) {
                if ("".equals(line)) {
                    break;
                }
                String[] str = line.split("\\|");
                String s1 = str[0].replace("\"", "");
                String s2 = str[1].replace("\"", "");
                String s3 = str[2].replace("\"", "");
                String s4 = str[3].replace("\"", "");
                String s5 = str[4].replace("\"", "");

                System.out.println(s1 + " - " + s2 + " - " + s3 + " - " + s4 + " - " + s5 + " - ");

            }
        }
    }


    public static void main(String[] args) throws IOException {

//        String path = "C:\\Users\\EDZ\\Desktop\\qiancang-through-master\\qiancang-through-master\\commons\\commons-model";
//        getFiles(path);

//        dongDeConnection();
//        List<String> excel = excel();
//        System.out.println(excel);
//        line(excel, "C:\\Users\\...\\Desktop\\test\\test.txt");

        getFile();
    }

}
