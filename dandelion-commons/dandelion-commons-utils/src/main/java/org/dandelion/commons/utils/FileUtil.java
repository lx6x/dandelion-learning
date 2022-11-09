package org.dandelion.commons.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

    public static void main(String[] args) throws IOException {

        String path = "C:\\Users\\EDZ\\Desktop\\qiancang-through-master\\qiancang-through-master\\commons\\commons-model";
        getFiles(path);

    }

}
