package org.dandelion.flowable.flowable.converter;

import org.dandelion.flowable.flowable.model.entity.ActDeModel;
import org.flowable.ui.modeler.domain.Model;
import org.dandelion.flowable.flowable.model.vo.ActDeModelVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>
 * 类转换
 * </p>
 *
 * @author lx6x
 * @since 2023/07/14
 */
@Mapper(componentModel = "spring")
public abstract class ModelConverter {

    public abstract ActDeModelVO do2vo(ActDeModel actDeModel);

    public abstract List<ActDeModelVO> do2vo(List<ActDeModel> actDeModel);
    public abstract ActDeModelVO do2vo(Model model);

}
