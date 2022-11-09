package org.dandelion.scheduling.xxljob.mock;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/10/14 16:45
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public abstract class AbstractSpringMvcTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    protected MockMvc mockMvc;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }


}
