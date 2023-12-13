package org.dandelion.mybatis.flex.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.dandelion.mybatis.flex.entity.User;

/**
 * @author lx6x
 * @date 2023/12/13
 */
public interface UserMapper extends BaseMapper<User> {

    User selectByName(@Param("name") String name);
}
