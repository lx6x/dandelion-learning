package org.dandelion.easyexcel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.dandelion.easyexcel.domain.User;
import org.dandelion.easyexcel.mapper.UserMapper;
import org.dandelion.easyexcel.service.IUserService;
import org.springframework.beans.BeansException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lx6x
 * @since 2023/08/09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    //一个sheet装100w数据
    public static final Integer PER_SHEET_ROW_COUNT = 1000000;
    //每次查询20w数据，每次写入20w数据
    public static final Integer PER_WRITE_ROW_COUNT = 200000;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void export() throws IOException {
        OutputStream outputStream = null;
        try {
            //记录总数:实际中需要根据查询条件进行统计即可
            //LambdaQueryWrapper<Emp> lambdaQueryWrapper = new QueryWrapper<Emp>().lambda().eq(Emp::getEmpno, 1000001);
            Integer totalCount = (int) count();
            //每一个Sheet存放100w条数据
            Integer sheetDataRows = PER_SHEET_ROW_COUNT;
            //每次写入的数据量20w,每页查询20W
            int writeDataRows = PER_WRITE_ROW_COUNT;
            //计算需要的Sheet数量
            int sheetNum = totalCount % sheetDataRows == 0 ? (totalCount / sheetDataRows) : (totalCount / sheetDataRows + 1);
            //计算一般情况下每一个Sheet需要写入的次数(一般情况不包含最后一个sheet,因为最后一个sheet不确定会写入多少条数据)
            int oneSheetWriteCount = sheetDataRows / writeDataRows;
            //计算最后一个sheet需要写入的次数
            int lastSheetWriteCount = totalCount % sheetDataRows == 0 ? oneSheetWriteCount : (totalCount % sheetDataRows % writeDataRows == 0 ? (totalCount / sheetDataRows / writeDataRows) : (totalCount / sheetDataRows / writeDataRows + 1));

            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = null;
            if (requestAttributes != null) {
                response = requestAttributes.getResponse();
            }
            if (response != null) {
                outputStream = response.getOutputStream();
            }
            //必须放到循环外，否则会刷新流
            ExcelWriter excelWriter = EasyExcel.write(outputStream).build();

            //开始分批查询分次写入
            for (int i = 0; i < sheetNum; i++) {
                //创建Sheet
                WriteSheet sheet = new WriteSheet();
                sheet.setSheetName("测试写入Sheet" + i);
                sheet.setSheetNo(i);
                //循环写入次数: j的自增条件是当不是最后一个Sheet的时候写入次数为正常的每个Sheet写入的次数,如果是最后一个就需要使用计算的次数lastSheetWriteCount
                for (int j = 0; j < (i != sheetNum - 1 ? oneSheetWriteCount : lastSheetWriteCount); j++) {
                    //分页查询一次20w
                    Page<User> page = page(new Page(j + 1 + (long) oneSheetWriteCount * i, writeDataRows));
                    List<User> userList = page.getRecords();

                    WriteSheet writeSheet = EasyExcel.writerSheet(i, "用户信息" + (i + 1)).head(User.class)
                            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
                    //写数据
                    excelWriter.write(userList, writeSheet);
                }
            }
            // 下载EXCEL
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止浏览器端导出excel文件名中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("用户信息", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            excelWriter.finish();
            if (outputStream != null) {
                outputStream.flush();
            }
        } catch (IOException | BeansException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    @Override
    public void importData(List<Map<Integer, String>> dataList) {
        //结果集中数据为0时,结束方法.进行下一次调用
        if (dataList.isEmpty()) {
            return;
        }
        DataSource dataSource = jdbcTemplate.getDataSource();
        //JDBC分批插入+事务操作完成对20w数据的插入
        PreparedStatement ps;
        Connection conn;

        try {
            if (dataSource != null) {
                conn = dataSource.getConnection();
                long startTime = System.currentTimeMillis();
                System.out.println(dataList.size() + "条,开始导入到数据库时间:" + startTime + "ms");

                //控制事务:默认不提交
                conn.setAutoCommit(false);
                String sql = "insert into user (`id`, `name`, `sex`, `age`, `create_date`) values";
                sql += "(?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                //循环结果集:这里循环不支持lambda表达式
                for (int i = 0; i < dataList.size(); i++) {
                    Map<Integer, String> item = dataList.get(i);
                    ps.setString(1, item.get(0));
                    ps.setString(2, item.get(1));
                    ps.setString(3, item.get(2));
                    ps.setString(4, item.get(3));
                    ps.setString(5, item.get(4));
                    //将一组参数添加到此 PreparedStatement 对象的批处理命令中。
                    ps.addBatch();
                }
                //执行批处理
                ps.executeBatch();
                //手动提交事务
                conn.commit();
                long endTime = System.currentTimeMillis();
                System.out.println(dataList.size() + "条,结束导入到数据库时间:" + endTime + "ms");
                System.out.println(dataList.size() + "条,导入用时:" + (endTime - startTime) + "ms");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
