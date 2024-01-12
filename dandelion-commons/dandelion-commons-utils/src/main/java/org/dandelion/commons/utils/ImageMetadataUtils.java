package org.dandelion.commons.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 获取图片和视频文件的Exif信息
 *
 * @author lx6x
 * @date 2024/1/12
 */
public class ImageMetadataUtils {
    /*
      <dependency>
        <groupId>com.drewnoakes</groupId>
        <artifactId>metadata-extractor</artifactId>
        <version>2.18.0</version>
      </dependency>
     */
    public static void main(String[] args) throws IOException, ImageProcessingException {
        Path path = Paths.get("1.png");
        InputStream inputStream = Files.newInputStream(path);
        Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
        Iterable<Directory> directories = metadata.getDirectories();
        for (Directory directory : directories) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName();  //标签名
                String desc = tag.getDescription(); //标签信息
                System.out.println(tagName + "===" + desc);//照片信息
            }
        }
    }
}
