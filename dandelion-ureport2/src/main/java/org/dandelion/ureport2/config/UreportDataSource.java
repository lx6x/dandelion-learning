package org.dandelion.ureport2.config;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/07/01 16:33
 */
public class UreportDataSource implements BuildinDatasource {
    //控制台日志打印
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private DataSource dataSource;//通过dataSource对象来获取数据库连接对象并且返回给uReport2

    @Override
    public Connection getConnection() {
        try {
            //此处的dataSource是加载application.properties文件中的数据库连接信息，如需打开其他数据库，笔者可以在此处添加其他数据库连接信息源来返回连接对象即可
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Ureport 数据源 获取连接失败！");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String name() {
        //此处为数据源的名称，可以根据自己的情况进行编写
        return "uReport数据源";
    }

}
