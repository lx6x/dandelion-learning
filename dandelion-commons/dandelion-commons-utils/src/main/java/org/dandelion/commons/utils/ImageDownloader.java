package org.dandelion.commons.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;

/**
 * TODO
 *
 * @author lx6x
 */
public class ImageDownloader {

    public static void downloadImage(String imageUrl, String destinationDir) {
        try {
            // 创建URL对象
            URL url = new URL(imageUrl);

            // 从URL中提取文件名和扩展名
            String fileName = extractFileName(url);

            // 构建保存图片的完整路径
            String destinationPath = Paths.get(destinationDir, fileName).toString();


            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            // 获取输入流
            InputStream inputStream = connection.getInputStream();

            // 创建输出流，将图片保存到文件
            FileOutputStream outputStream = new FileOutputStream(destinationPath);

            // 创建缓冲区
            byte[] buffer = new byte[4096];
            int bytesRead;

            // 写入文件
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // 关闭资源
            outputStream.close();
            inputStream.close();

            System.out.println("图片下载成功，保存路径: " + destinationPath);

        } catch (IOException e) {
            System.out.println("下载图片时发生错误: " + e.getMessage());
        }
    }

    // 从URL中提取文件名和扩展名
    private static String extractFileName(URL url) {
        String path = url.getPath();  // 获取URL中的路径部分
        // 提取文件名部分
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public static void main(String[] args) {

        String[] images = {
                ""
        };

        String per = "xxx";

        for (String image : images) {
            String imageUrl = per.concat(image);
            String destinationDir = "\\images";
            downloadImage(imageUrl, destinationDir);
        }
    }
}
