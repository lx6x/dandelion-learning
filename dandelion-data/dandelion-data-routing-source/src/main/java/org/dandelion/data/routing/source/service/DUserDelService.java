package org.dandelion.data.routing.source.service;

import org.dandelion.data.routing.source.annotation.DataSource;
import org.dandelion.data.routing.source.entity.DUserDel;
import org.dandelion.data.routing.source.entity.DUserDelExample;
import org.dandelion.data.routing.source.enums.DataSourceType;
import org.dandelion.data.routing.source.mapper.DUserDelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/6/8 16:33
 */
@Service
public class DUserDelService {

    @Autowired
    private DUserDelMapper dUserDelMapper;

    @DataSource(value = DataSourceType.MASTER)
    public List<DUserDel> master() {
        return dUserDelMapper.selectByExample(new DUserDelExample());
    }

    @DataSource(value = DataSourceType.SLAVE)
    public List<DUserDel> slave() {
        return dUserDelMapper.selectByExample(new DUserDelExample());
    }

    /**
     * 手动配置数据源
     *
     * @return java.util.List<org.dandelion.data.routing.source.entity.DUserDel>
     * @author L
     */
    public List<DUserDel> def() {
        return dUserDelMapper.selectByExample(new DUserDelExample());
    }
}
