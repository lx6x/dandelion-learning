package org.dandelion.libre.office.example;

import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.office.OfficeManager;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;

import java.io.File;

public class ExcelToPdfConverter {

    public static void main(String[] args) throws OfficeException {
        // 启动 LibreOffice 服务
        OfficeManager officeManager = LocalOfficeManager.builder().officeHome("F:\\Program Files\\LibreOffice").install().build();
        officeManager.start();

        try {
            // 创建 DocumentConverter
            DocumentConverter converter = LocalConverter.builder().officeManager(officeManager).build();

            // 输入的 Excel 文件
//            File inputFile = new File("C:\\Users\\xiayo\\Desktop\\1.xlsx");
//            File inputFile = new File("C:\\Users\\xiayo\\Desktop\\1xlsx.html");
            File inputFile = new File("C:\\Users\\xiayo\\Desktop\\1.png");

            // 输出的 PDF 文件
//            File outputFile = new File("C:\\Users\\xiayo\\Desktop\\1xlsx.html");
            File outputFile = new File("C:\\Users\\xiayo\\Desktop\\1png.pdf");

            // 进行转换
            converter.convert(inputFile).to(outputFile).execute();

            System.out.println("Conversion completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭 LibreOffice 服务
            officeManager.stop();
        }
    }
}
