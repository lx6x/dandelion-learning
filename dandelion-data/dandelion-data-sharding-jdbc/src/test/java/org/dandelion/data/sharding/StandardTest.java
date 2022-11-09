package org.dandelion.data.sharding;

import com.alibaba.fastjson.JSONObject;
import org.dandelion.data.sharding.entity.User;
import org.dandelion.data.sharding.entity.UserDel;
import org.dandelion.data.sharding.mapper.UserDelMapper;
import org.dandelion.data.sharding.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/3/29 18:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingSphereJdbcApplication.class)
public class StandardTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDelMapper userDelMapper;

    @Test
    public void test1(){
        User user1=new User();
        user1.setId(2+"");
        user1.setUserId(1);

        User user = userMapper.getByUser(user1);
        System.out.println(user);
    }

    @Test
    public void test2(){
        User user = userMapper.getById(1);
        System.out.println(user);
    }

    @Test
    public void test3(){
        User user = userMapper.getByName("张三");
        System.out.println(user);
    }

    @Test
    public void test4(){
        for (int i = 1;i<=10;i++){
            User user=new User();
//            user.setId(1);
            user.setName("张三 - - "+i);
            user.setPhone(i+"001");
            user.setUserId(i);
            int saveByUser = userMapper.saveByUser(user);
            System.out.println(saveByUser);
        }
    }

    @Test
    public void test5(){
        for (int i = 1;i<=10;i++){
            User user=new User();
            user.setId(i+"");
            user.setName("张三 - - "+i);
            user.setPhone(i+"001");
            userMapper.saveByUser(user);

            UserDel userDel=new UserDel();
            userDel.setId(i);
            userDel.setUId(i);
            userDel.setAddress(String.valueOf(System.currentTimeMillis()));
            userDelMapper.saveByUserDel(userDel);
        }
    }

    @Test
    public void test6(){
        User user=new User();
        user.setId(1+"");
        List<Map<String, Object>> userAndUserDel = userMapper.getUserAndUserDel(user);
        System.out.println(JSONObject.toJSONString(userAndUserDel));
    }
}
