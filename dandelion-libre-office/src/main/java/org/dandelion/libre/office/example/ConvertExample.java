package org.dandelion.libre.office.example;

import jakarta.annotation.Resource;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DocumentFormat;
import org.jodconverter.core.office.OfficeException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @author lx6x
 * @date 2023/12/14
 */
@Service
public class ConvertExample {

    public static String convertToPDF() {
        try {
            // 定义LibreOffice命令
            String libreOfficeCommand = "libreoffice --headless --convert-to pdf /path/to/your/document.docx";

            // 执行命令
            Process process = Runtime.getRuntime().exec(libreOfficeCommand);

            // 等待进程执行完成
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return "Conversion successful!";
            } else {
                return "Conversion failed!";
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error during conversion: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        convertToPDF();
    }

    @Resource
    private DocumentConverter documentConverter;

    public void convertDocument(String sourcePath, DocumentFormat sourceType, String targetPath, DocumentFormat targetType) throws OfficeException {
        // 调用jodconverter提供的方法执行文档转换
        documentConverter.convert(new File(sourcePath)).as(sourceType).to(new File(targetPath)).as(targetType).execute();
    }
}
