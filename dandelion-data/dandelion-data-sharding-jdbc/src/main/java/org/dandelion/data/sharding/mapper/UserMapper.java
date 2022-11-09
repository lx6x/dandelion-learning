package org.dandelion.data.sharding.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dandelion.data.sharding.entity.User;

import java.util.List;
import java.util.Map;

/**
 * TODO inline
 *
 * @author L
 * @version 1.0
 * @date 2022/3/29 15:13
 */
@Mapper
public interface UserMapper {


    User getByUser(User user);

    User getById(Integer id);

    User getByName(String name);

    int saveByUser(User user);

    List<Map<String,Object>> getUserAndUserDel(User user);
}
