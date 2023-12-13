package org.dandelion.mybatis.flex;

import jakarta.annotation.Resource;
import org.dandelion.mybatis.flex.entity.User;
import org.dandelion.mybatis.flex.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author lx6x
 * @date 2023/12/13
 */
@SpringBootTest
public class MybatisFlexApplicationTest {


    @Resource
    private UserMapper userMapper;

    @Test
    public void getAll() {
        List<User> users = userMapper.selectAll();
        System.out.println(users);
    }

    @Test
    public void getByName() {
        User user = userMapper.selectByName("张三");
        System.out.println(user);

    }
}
