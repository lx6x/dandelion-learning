package org.dandelion.easyexcel.controller;


import com.alibaba.excel.EasyExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.dandelion.easyexcel.listeners.EasyExcelGeneralDataListener;
import org.dandelion.easyexcel.service.IUserService;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 读 excel 操作
 *
 * @author lx6x
 * @date 2023/8/9
 */
@Tag(name = "excel 操作")
@RestController
@RequestMapping("/excel")
public class EasyexcelController {

    @Resource
    private IUserService userService;

    /**
     * 分批次导出
     */
    @Operation(summary = "分批次导出")
    @GetMapping("/export")
    public void export() throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        userService.export();
        stopWatch.stop();
        System.out.println("共计耗时： " + stopWatch.getTotalTimeSeconds() + "S");
    }

    /**
     * 分批次导入
     */
    @Operation(summary = "分批次导入")
    @GetMapping("/importData")
    public void importData() {
        String fileName = "C:\\Users\\zk\\Desktop\\easyexcel\\用户信息.xlsx";
        //记录开始读取Excel时间,也是导入程序开始时间
        long startReadTime = System.currentTimeMillis();
        System.out.println("------开始读取Excel的Sheet时间(包括导入数据过程):" + startReadTime + "ms------");
        //读取所有Sheet的数据.每次读完一个Sheet就会调用这个方法
        EasyExcel.read(fileName, new EasyExcelGeneralDataListener(userService)).doReadAll();
        long endReadTime = System.currentTimeMillis();
        System.out.println("------结束读取Excel的Sheet时间(包括导入数据过程):" + endReadTime + "ms------");
        System.out.println("------读取Excel的Sheet时间(包括导入数据)共计耗时:" + (endReadTime-startReadTime) + "ms------");
    }

}
