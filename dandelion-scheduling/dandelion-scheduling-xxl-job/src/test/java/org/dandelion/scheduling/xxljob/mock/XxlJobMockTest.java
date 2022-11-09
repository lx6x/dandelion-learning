package org.dandelion.scheduling.xxljob.mock;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/10/14 16:40
 */
public class XxlJobMockTest extends AbstractSpringMvcTest {

    @Test
    public void getTest() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("s", "1");
        MvcResult ret = mockMvc.perform(
                get("/xxl/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .params(params)
        ).andReturn();
        System.out.println("返回：" + ret.getResponse().getContentAsString());
    }

    @Test
    public void postTest() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", "2");
        String s = JSONObject.toJSONString(map);
        MvcResult ret = mockMvc.perform(
                post("/xxl/test1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s)
        ).andReturn();
        String contentAsString = ret.getResponse().getContentAsString();
        System.err.println(contentAsString);
    }
}
