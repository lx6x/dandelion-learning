package org.dandelion.libre.office.example;

import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExcelToPdfConverter {

    public static void fileToPdf() throws OfficeException {
        // 启动 LibreOffice 服务
        OfficeManager officeManager = LocalOfficeManager.builder().taskExecutionTimeout(1200000L).officeHome("F:\\Program Files\\LibreOffice").install().build();
        officeManager.start();

        try {
            // 创建 DocumentConverter
            DocumentConverter converter = LocalConverter.builder().officeManager(officeManager).build();

            // 输入的 Excel 文件
//            File inputFile = new File("C:\\Users\\xiayo\\Desktop\\1.xlsx");
//            File inputFile = new File("C:\\Users\\xiayo\\Desktop\\1xlsx.html");
//            File inputFile = new File("C:\\Users\\xiayo\\Desktop\\1.png");
            File inputFile = new File("C:\\Users\\zk\\Desktop\\1.doc");

            // 输出的 PDF 文件
//            File outputFile = new File("C:\\Users\\xiayo\\Desktop\\1xlsx.html");
            File outputFile = new File("C:\\Users\\zk\\Desktop\\1doc.pdf");

            long startTime = System.currentTimeMillis();
            // 进行转换
            converter.convert(inputFile).to(outputFile).execute();

            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);  // 以毫秒为单位
            System.out.println(duration + "  ms");

            System.out.println("Conversion completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭 LibreOffice 服务
            officeManager.stop();
        }
    }

    public static void urlToPdf() throws IOException, OfficeException {
        String fileUrl = "http://0.0.0.0:0000/group1/06.png";
        String outFilePath="";
        URL url = new URL(fileUrl);
        URLConnection connection = url.openConnection();

        InputStream inputStream = connection.getInputStream();

        // 启动 LibreOffice 服务
        OfficeManager officeManager = LocalOfficeManager.builder().taskExecutionTimeout(1200000L).officeHome("F:\\Program Files\\LibreOffice").install().build();
        officeManager.start();

        // 创建 DocumentConverter
        DocumentConverter converter = LocalConverter.builder().officeManager(officeManager).build();

        File outputFile = new File("C:\\Users\\zk\\Desktop\\06.pdf");
        converter.convert(inputStream).to(outputFile).execute();

    }

    public static void main(String[] args) throws OfficeException, IOException {
        urlToPdf();
    }
}
