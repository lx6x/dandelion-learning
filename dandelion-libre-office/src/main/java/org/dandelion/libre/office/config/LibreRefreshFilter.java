package org.dandelion.libre.office.config;

import com.sun.star.awt.Size;
import com.sun.star.beans.PropertyValue;
import com.sun.star.lang.XComponent;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.view.PaperFormat;
import com.sun.star.view.XPrintable;
import org.jodconverter.core.office.OfficeContext;
import org.jodconverter.local.filter.FilterChain;
import org.jodconverter.local.filter.RefreshFilter;

/**
 * 创建文件转换器
 *
 * @author lx6x
 * @date 2023/12/14
 */
public class LibreRefreshFilter extends RefreshFilter {
    @Override
    public void doFilter(OfficeContext context, XComponent document, FilterChain chain) throws Exception {

       refreshDocument(document);

        super.doFilter(context, document, chain);
    }

    public final static Size A5, A4, A3;
    public final static Size B4, B5, B6;
    public final static Size paperSize;
    static {
        A5 = new Size(14800, 21000);
        A4 = new Size(21000, 29700);
        A3 = new Size(29700, 42000);
        B4 = new Size(25000, 35300);
        B5 = new Size(17600, 25000);
        B6 = new Size(12500, 17600);
        // 最大限度 宽 1600000
        paperSize = new Size(29700, 27940);
    }

    protected void refreshDocument(XComponent document) {
        XPrintable xPrintable = UnoRuntime.queryInterface(XPrintable.class, document);
        PropertyValue[] printerDesc = new PropertyValue[2];
        // 转换
        printerDesc[0] = new PropertyValue();
        printerDesc[0].Name = "PaperFormat";
        printerDesc[0].Value = PaperFormat.USER;
        // 纸张大小
        printerDesc[1] = new PropertyValue();
        printerDesc[1].Name = "PaperSize";
        printerDesc[1].Value = paperSize;
        try {
            xPrintable.setPrinter(printerDesc);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
