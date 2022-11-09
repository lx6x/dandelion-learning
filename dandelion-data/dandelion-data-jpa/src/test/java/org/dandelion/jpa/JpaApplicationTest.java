package org.dandelion.jpa;

import org.dandelion.jpa.dao.UserRepository;
import org.dandelion.jpa.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/3/2 17:54
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JpaApplicationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void add() {

        for (int i = 0; i < 10; i++) {
            User user = new User();
//            user.setId(i);
            user.setName(""+i);
            user.setAge(i);
            System.err.println(user);
            userRepository.save(user);
        }
    }

    @Test
    public void list(){
        List<User> all = userRepository.findAll();
        System.err.println(all);
    }

}
