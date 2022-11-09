package org.dandelion.oss.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TODO
 *
 * @author LJF
 * @version 1.0
 * @date 2021/12/11 15:46
 */
@Mapper
public interface OssDao {

    List<Object> selectOss();

}
