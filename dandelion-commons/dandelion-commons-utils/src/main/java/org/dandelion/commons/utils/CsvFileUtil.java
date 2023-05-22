package org.dandelion.commons.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022-12-27
 */
public class CsvFileUtil {

    //	析csv文件并转成bean（方法一）
    public static List<String> getCsvDataMethod1() throws FileNotFoundException {
        ArrayList<String> csvFileList = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\...\\Desktop\\flow.csv"));

        InputStreamReader in;
        String s = null;
        try {
            in = new InputStreamReader(fileInputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(in);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");
                String s1 = split[0];
                String s2 = split[1];
                String s3 = split[2];
                String s4 = split[3];

                System.out.println(s1 + " " + s2 + " " + s3 + " " + s4);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFileList;
    }

    public static void main(String[] args) {
        /*try {
            getCsvDataMethod1();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }*/

        List<String> list=new ArrayList<>();

        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");

        for (String s : list) {
            if(s.equals("1") || s.equals("4")){
                list.remove(s);
            }
        }
        System.out.println(list.toString());
    }
}
