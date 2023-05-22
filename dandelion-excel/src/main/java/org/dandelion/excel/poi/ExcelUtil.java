package org.dandelion.excel.poi;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/06/23 14:43
 */
public class ExcelUtil {

    public static void main(String[] args) throws FileNotFoundException {
        File file=new File("C:\\Users\\...\\Desktop\\相关方人员.xlsx");

        InputStream in = new FileInputStream(file);

        ExcelUtil excelUtil=new ExcelUtil();
        excelUtil.addReportByExcel(in,"相关方人员.xlsx");
    }

    public String addReportByExcel(InputStream inputStream, String fileName) {
        String message = "Import success";

        boolean isE2007 = false;    //格式判断
        if (fileName.endsWith("xlsx")) {
            isE2007 = true;
        }

        int rowIndex = 0;
        int columnIndex = 0;
        try {
            InputStream input = inputStream;  //建立输入流
            Workbook wb = null;
            if (isE2007) {
                wb = new XSSFWorkbook(input);
            } else {
                wb = new HSSFWorkbook(input);
            }
            Sheet sheet = wb.getSheetAt(0);    //获得第一个表单

            System.out.println("总行数:"+sheet.getLastRowNum());

            List<CellRangeAddress> cras = getCombineCell(sheet);
            int count = sheet.getLastRowNum() + 1;//总行数

            List<TPartnerStaff> irs = new ArrayList<>();
            for (int i = 2; i < count; i++) {
                rowIndex = i;
                Row row = sheet.getRow(i);
                TPartnerStaff ir = new TPartnerStaff();

                ir.setPartnerId(getCellValue(row.getCell(3)));
                ir.setName(getCellValue(row.getCell(5)));
                ir.setIdcard(getCellValue(row.getCell(6)));
                ir.setPhone(getCellValue(row.getCell(7)));
                ir.setWorkType(getCellValue(row.getCell(8)));

                List<TPartnerCertificate> items = new ArrayList<>();
                if (isMergedRegion(sheet, i, 0)) {
                    int lastRow = getRowNum(cras, sheet.getRow(i).getCell(0), sheet);

                    for (; i <= lastRow; i++) {
                        row = sheet.getRow(i);
                        TPartnerCertificate item = new TPartnerCertificate();
                        item.setName(getCellValue(row.getCell(9)));
                        item.setCode(getCellValue(row.getCell(10)));
                        item.setValidEndDate(getCellValue(row.getCell(11)));
                        items.add(item);
                    }
                    i--;
                } else {
                    row = sheet.getRow(i);
                    TPartnerCertificate item = new TPartnerCertificate();
                    item.setName(getCellValue(row.getCell(9)));
                    item.setCode(getCellValue(row.getCell(10)));
                    item.setValidEndDate(getCellValue(row.getCell(11)));
                    items.add(item);
                }
                ir.setItems(items);
                irs.add(ir);

            }

            for (TPartnerStaff inspectionReport: irs){
                System.out.println(JSONObject.toJSONString(inspectionReport));
            }




        } catch (Exception ex) {
            return "error";
        }
        return message;
    }

    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    public String getCellValue(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        }
        return "";
    }

    /**
     * 合并单元格处理,获取合并行
     *
     * @param sheet
     * @return List<CellRangeAddress>
     */
    public List<CellRangeAddress> getCombineCell(Sheet sheet) {
        List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
        //获得一个 sheet 中合并单元格的数量
        int sheetmergerCount = sheet.getNumMergedRegions();
        //遍历所有的合并单元格
        for (int i = 0; i < sheetmergerCount; i++) {
            //获得合并单元格保存进list中
            CellRangeAddress ca = sheet.getMergedRegion(i);
            list.add(ca);
        }
        return list;
    }

    private int getRowNum(List<CellRangeAddress> listCombineCell, Cell cell, Sheet sheet) {
        int xr = 0;
        int firstC = 0;
        int lastC = 0;
        int firstR = 0;
        int lastR = 0;
        for (CellRangeAddress ca : listCombineCell) {
            //获得合并单元格的起始行, 结束行, 起始列, 结束列
            firstC = ca.getFirstColumn();
            lastC = ca.getLastColumn();
            firstR = ca.getFirstRow();
            lastR = ca.getLastRow();
            if (cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR) {
                if (cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC) {
                    xr = lastR;
                }
            }

        }
        return xr;

    }

    /**
     * 判断单元格是否为合并单元格，是的话则将单元格的值返回
     *
     * @param listCombineCell 存放合并单元格的list
     * @param cell            需要判断的单元格
     * @param sheet           sheet
     * @return
     */
    public String isCombineCell(List<CellRangeAddress> listCombineCell, Cell cell, Sheet sheet)
        throws Exception {
        int firstC = 0;
        int lastC = 0;
        int firstR = 0;
        int lastR = 0;
        String cellValue = null;
        for (CellRangeAddress ca : listCombineCell) {
            //获得合并单元格的起始行, 结束行, 起始列, 结束列
            firstC = ca.getFirstColumn();
            lastC = ca.getLastColumn();
            firstR = ca.getFirstRow();
            lastR = ca.getLastRow();
            if (cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR) {
                if (cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC) {
                    Row fRow = sheet.getRow(firstR);
                    Cell fCell = fRow.getCell(firstC);
                    cellValue = getCellValue(fCell);
                    break;
                }
            } else {
                cellValue = "";
            }
        }
        return cellValue;
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }

        return null;
    }


    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet
     * @param row    行下标
     * @param column 列下标
     * @return
     */
    private boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

}
