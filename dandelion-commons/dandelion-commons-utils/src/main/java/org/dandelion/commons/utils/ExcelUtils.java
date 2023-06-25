package org.dandelion.commons.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @date 2021/11/22 16:09
 * @since 1.0.0
 */
public class ExcelUtils {

    /**
     * 导出excel <p/>
     * <p>
     * 注意：<p/>
     * HashMap 生成时数据是无序的 <p/>
     * LinkedHashMap 生成时数据是有序的
     *
     * @param list     数据
     * @param fileName 导出excel文件名称
     * @return excel字节码文件
     * @throws IOException io
     */
    /*public static ResponseEntity<byte[]> createExcel(List<Map<String, Object>> list, String fileName) throws IOException {
        File file = new File(generateExcel(list));
        //指定响应文件名 并指定文件名称响应编码
        return FileUtils.download(FileUtils.getByte(file), new String((fileName + ".xlsx").getBytes(), "iso8859-1"));
    }*/
    public static ResponseEntity<byte[]> createCsv(Object[] titles, Object[] properties, List<Map<String, Object>> list, String fileName) throws Exception {
        File file = new File(exportCsv(titles, properties, list));
        //指定响应文件名 并指定文件名称响应编码
        return FileUtils.download(FileUtils.getByte(file), new String((fileName + ".csv").getBytes(), "iso8859-1"));
    }

    public static ResponseEntity<byte[]> createExcel(String path, String fileName) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(path);
        InputStream inputStream = classPathResource.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = inputStream.read(buffer))) {
            output.write(buffer, 0, n);
        }
        try {
            //指定响应文件名 并指定文件名称响应编码
            return FileUtils.download(output.toByteArray(), new String((fileName + ".xlsx").getBytes(), "iso8859-1"));
        } finally {
            output.close();
            inputStream.close();
        }
    }

    /**
     * 解析 Excel数据
     *
     * @param excel excel
     * @param keys  数据 keys
     * @return data
     * @throws Exception keys长度 与 excel中的列数不一致
     */
    public static List<Map<String, String>> analysisExcel(MultipartFile excel, String... keys) throws Exception {
        return (List<Map<String, String>>) analysisExcelData(excel, null, keys);
    }

    public static <T> List<T> analysisExcel(MultipartFile excel, Class<T> cla, String... properties) throws Exception {
        return (List<T>) analysisExcelData(excel, cla, properties);
    }

    public static List<Map<String, Object>> analysisExcel(MultipartFile excel) throws Exception {
        return analysisExcelData(excel);
    }

    private static <T> Object analysisExcelData(MultipartFile excel, Class<T> cla, String[] keys) throws Exception {

        long size = excel.getSize();


        List<Map<String, Object>> list = analysisExcelData(excel);
        if (list.size() <= 0) {
            return list;
        }
        if (cla != null) {
            List<T> entityList = new LinkedList<>();
            for (Map<String, Object> map : list) {
                T t = cla.newInstance();
                final int[] i = {0};
                map.forEach((key, value) -> {
//                    System.err.println(key + "  ===>>>  " + keys[i[0]]);
                    try {
                        Field field = t.getClass().getDeclaredField(keys[i[0]]);
                        field.setAccessible(true);
                        field.set(t, value);
                        entityList.add(t);
                        ++i[0];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            return entityList;
        }
        List<Map<String, String>> data = new LinkedList<>();
        for (Map<String, Object> map : list) {
            Map<String, String> rowMap = new LinkedHashMap<>();
            final int[] f = {0};
            map.forEach((key, value) -> {
//                System.err.println(key + "  ===>>>  " + keys[f[0]]);
                String key1 = keys[f[0]];
                rowMap.put(key1, String.valueOf(value));
                ++f[0];
            });
            data.add(rowMap);
        }
        return data;

//        InputStream inputStream = null;
//        try {
//            inputStream = excel.getInputStream();
//        } catch (Exception e) {
//            throw new GlobalException("获取文件流失败");
//        }
//        ExcelReader reader = ExcelUtil.getReader(inputStream, 0); //指定输入流和sheet
//        // 读取第二行到最后一行数据
//        List<List<Object>> read = reader.read(1, reader.getRowCount());
//        if (read.size() > 0) {
//            ThrowUtils.isTrue(read.get(0).size() == keys.length, "keys 与 excel列数量 不一致");
//        }
//        if (cla != null) {
//            List<T> data = new LinkedList<>();
//            for (List<Object> list : read) {
//                T t = cla.newInstance();
//                for (int i = 0; i < keys.length; i++) {
//                    Field field = t.getClass().getDeclaredField(keys[i]);
//                    field.setAccessible(true);
//                    field.set(t, list.get(i));
//                }
//                data.add(t);
//            }
//            return data;
//        }
//        List<Map<String, String>> data = new LinkedList<>();
//        for (List<Object> list : read) {
//            Map<String, String> map = new LinkedHashMap<>();
//            for (int i = 0; i < keys.length; i++) {
//                map.put(keys[i], String.valueOf(list.get(i)));
//            }
//            data.add(map);
//        }
//        return data;

    }

    public static List<Map<String, Object>> analysisExcelData(MultipartFile excel) {
        InputStream inputStream = null;
        try {
            inputStream = excel.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExcelReader reader = ExcelUtil.getReader(inputStream, 0); //指定输入流和sheet
        return reader.readAll();
    }


    /**
     * 生成Excel 文件（临时 用完就删）<p/>
     * <p>
     * 注意：<p/>
     * HashMap 生成时数据是无序的 <p/>
     * LinkedHashMap 生成时数据是有序的
     *
     * @param list Excel 文件中的数据
     * @return 生成Excel文件路径
     * @throws IOException io
     */
    /*public static synchronized String generateExcel(List<Map<String, Object>> list) throws IOException {
        String tempPath = System.getProperty("java.io.tmpdir") + "/" + IdUtil.fastSimpleUUID() + ".xlsx";
        File file = new File(tempPath);
        BigExcelWriter writer = ExcelUtil.getBigWriter(file);
        //导出的excel 所有列设为文本格式
        DataFormat format = writer.getWorkbook().createDataFormat();
        CellStyle style = writer.getOrCreateCellStyle("B2");
        style.setDataFormat(format.getFormat("@"));
        if (list != null && list.size() > 0) {
            writer.getSheets().forEach(sheet -> {
                for (int i = 0; i < list.get(0).size(); i++) {
                    sheet.setDefaultColumnStyle(i, style);
                    sheet.setColumnWidth(i, 5000);
                }
            });
        }
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        // 终止后删除临时文件
        file.deleteOnExit();
        writer.flush();
        return tempPath;
    }*/

    /**
     * 导出生成csv格式的文件
     *
     * @param titles     csv格式头文
     * @param properties 需要导出的数据实体的属性，注意与title一一对应
     * @param list       需要导出的对象集合
     * @return
     * @throws IOException              Created   2017年1月5日 上午10:51:44
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @author ccg
     */
    public static synchronized String exportCsv(Object[] titles, Object[] properties, List<Map<String, Object>> list) throws IOException, IllegalArgumentException, IllegalAccessException {
        String outPutPath = System.getProperty("java.io.tmpdir") + "/" + IdUtil.fastSimpleUUID() + ".csv";
        File file = new File(outPutPath);
        //构建输出流，同时指定编码
        OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file), "gbk");

        //csv文件是逗号分隔，除第一个外，每次写入一个单元格数据后需要输入逗号
        for (Object title : titles) {
            String s = title.toString();
            ow.write(s);
            ow.write(",");
        }
        //写完文件头后换行
        ow.write("\r\n");
        //写内容
        for (Map<String, Object> obj : list) {
            //遍历表头
            for (int i = 0; i < properties.length; i++) {
                Object property = properties[i];
                Object o = obj.get(property);
                if (!ObjectUtil.isEmpty(o)) {
                    String s = o.toString()
                            .replaceAll("\r\n", " ")
                            .replaceAll("\r", " ")
                            .replaceAll("\n", " ");
                    ow.write(s);
                } else {
                    ow.write("");
                }
                if (i != titles.length - 1) {
                    ow.write(",");
                }
            }

            //写完一行换行
            ow.write("\r\n");
        }
        ow.flush();
        ow.close();
        return outPutPath;
    }

}
