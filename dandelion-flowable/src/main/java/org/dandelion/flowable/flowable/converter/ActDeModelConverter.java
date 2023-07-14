package org.dandelion.flowable.flowable.converter;

import org.dandelion.flowable.flowable.model.entity.ActDeModelDO;
import org.dandelion.flowable.flowable.model.vo.ActDeModelVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author lx6x
 * @since 2023/07/14
 */
@Mapper(componentModel = "spring")
public abstract class ActDeModelConverter {

    public abstract ActDeModelVO do2vo(ActDeModelDO actDeModelDO);

    public abstract List<ActDeModelVO> do2vo(List<ActDeModelDO> actDeModelDO);
}
