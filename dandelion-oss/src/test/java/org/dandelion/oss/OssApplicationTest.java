package org.dandelion.oss;

import org.dandelion.oss.dao.OssDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * TODO
 *
 * @author LJF
 * @version 1.0
 * @date 2021/12/11 16:08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OssApplicationTest {

    @Autowired
    private OssDao ossDao;

    @Test
    public void test(){
        List<Object> objects = ossDao.selectOss();
        System.out.println(objects);
    }
}
