package org.dandelion.libre.office;

import org.dandelion.libre.office.example.ConvertExample;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.office.OfficeException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

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
//        String sourcePath = "C:\\Users\\zk\\Desktop\\1.xls";
//        String targetPath = "C:\\Users\\zk\\Desktop\\1xls.pdf";
        convertExample.convertDocument(sourcePath,DefaultDocumentFormatRegistry.XLS, targetPath, DefaultDocumentFormatRegistry.PDF);
    }

    @Test
    public void excelToOds() throws OfficeException {
        String sourcePath = "C:\\Users\\xiayo\\Desktop\\1.xlsx";
        String targetPath = "C:\\Users\\xiayo\\Desktop\\1xlsx.ods";
        convertExample.convertDocument(sourcePath,DefaultDocumentFormatRegistry.XLSX, targetPath,DefaultDocumentFormatRegistry.ODS);
    }

    @Test
    public void wordToPdf() throws OfficeException {
//        String sourcePath = "C:\\Users\\xiayo\\Desktop\\1.docx";
//        String targetPath = "C:\\Users\\xiayo\\Desktop\\1docx.pdf";

        String sourcePath = "C:\\Users\\zk\\Desktop\\1.doc";
        String targetPath = "C:\\Users\\zk\\Desktop\\1doc.pdf";
        convertExample.convertDocument(sourcePath,DefaultDocumentFormatRegistry.DOC, targetPath,DefaultDocumentFormatRegistry.PDF);
    }

}
