package org.dandelion.commons.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.GpsDirectory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

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

        // Metadata信息获取
        Iterable<Directory> directories = metadata.getDirectories();
        for (Directory directory : directories) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName();  //标签名
                String desc = tag.getDescription(); //标签信息
                System.out.printf("%-30s : %-10s \n",tagName, desc);//照片信息
            }
        }

        System.out.println();

        // 经纬度提取
        Collection<GpsDirectory> directoriesOfType = metadata.getDirectoriesOfType(GpsDirectory.class);
        for (GpsDirectory gpsDirectory : directoriesOfType) {
            GeoLocation geoLocation = gpsDirectory.getGeoLocation();
            System.out.println("经度："+geoLocation.getLongitude());
            System.out.println("纬度："+geoLocation.getLatitude());
            System.out.println("********************************************************");
        }

        System.out.println();
    }
}
