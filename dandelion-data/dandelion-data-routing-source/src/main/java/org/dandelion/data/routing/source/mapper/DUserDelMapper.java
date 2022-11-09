package org.dandelion.data.routing.source.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dandelion.data.routing.source.entity.DUserDel;
import org.dandelion.data.routing.source.entity.DUserDelExample;

import java.util.List;

@Mapper
public interface DUserDelMapper {
    int countByExample(DUserDelExample example);

    int deleteByExample(DUserDelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DUserDel record);

    int insertSelective(DUserDel record);

    List<DUserDel> selectByExample(DUserDelExample example);

    DUserDel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DUserDel record, @Param("example") DUserDelExample example);

    int updateByExample(@Param("record") DUserDel record, @Param("example") DUserDelExample example);

    int updateByPrimaryKeySelective(DUserDel record);

    int updateByPrimaryKey(DUserDel record);
}