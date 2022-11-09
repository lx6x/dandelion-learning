package org.dandelion.data.sharding.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dandelion.data.sharding.entity.UserDel;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/3/30 14:54
 */
@Mapper
public interface UserDelMapper {

    int saveByUserDel(UserDel userDel);
}
