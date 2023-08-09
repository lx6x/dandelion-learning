package org.dandelion.easyexcel.listeners;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.dandelion.easyexcel.service.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lx6x
 * @date 2023/8/9
 */
public class EasyExcelGeneralDataListener extends AnalysisEventListener<Map<Integer, String>> {

    //一个sheet装100w数据
    public static final Integer PER_SHEET_ROW_COUNT = 100000;

    /**
     * 处理业务逻辑的Service,也可以是Mapper
     */
    private IUserService userService;

    /**
     * 用于存储读取的数据
     */
    private List<Map<Integer, String>> dataList = new ArrayList<>();

    public EasyExcelGeneralDataListener() {
    }

    public EasyExcelGeneralDataListener(IUserService empService) {
        this.userService = empService;
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        //数据add进入集合
        dataList.add(data);
        //size是否为100000条:这里其实就是分批.当数据等于10w的时候执行一次插入
        if (dataList.size() >= PER_SHEET_ROW_COUNT) {
            //存入数据库:数据小于1w条使用Mybatis的批量插入即可;
            saveData();
            //清理集合便于GC回收
            dataList.clear();
        }
    }

    /**
     * 保存数据到DB
     */
    private void saveData() {
        userService.importData(dataList);
        dataList.clear();
    }

    /**
     * Excel中所有数据解析完毕会调用此方法
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        dataList.clear();
    }
}
