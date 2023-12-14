package org.dandelion.libre.office;

import jakarta.annotation.Resource;
import org.dandelion.libre.office.example.ConvertExample;
import org.jodconverter.core.office.OfficeException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author lx6x
 * @date 2023/12/13
 */
@SpringBootTest
public class LibreOfficeApplicationTest {
    @Resource
    private ConvertExample convertExample;

    @Test
    public void excelToPdf() throws OfficeException {
        String sourcePath = "C:\\Users\\xiayo\\Desktop\\1.xlsx";
        String targetPath = "C:\\Users\\xiayo\\Desktop\\1xlsx.pdf";
        convertExample.convertDocument(sourcePath, targetPath);
    }

    @Test
    public void wordToPdf() throws OfficeException {
        String sourcePath = "C:\\Users\\xiayo\\Desktop\\1.docx";
        String targetPath = "C:\\Users\\xiayo\\Desktop\\1docx.pdf";
        convertExample.convertDocument(sourcePath, targetPath);
    }

}
