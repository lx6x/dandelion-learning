package org.dandelion.easyexcel;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dandelion.easyexcel.domain.User;
import org.dandelion.easyexcel.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DandelionEasyexcelApplicationTests {

    @Autowired
    private IUserService userService;

    @Test
    void list() {
        Page<User> userPage = userService.page(new Page<>(1, 10));
        System.out.println(userPage.getRecords());
    }

}
