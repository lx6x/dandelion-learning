package org.dandelion.ureport2.controller;

import org.dandelion.ureport2.entity.User;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author lx6x
 * @date 2023/9/19
 */
@RestController
public class UreportController {

    public List<User> getUser(String dsName, String datasetName, Map<String, Object> parameters) {
        return new LinkedList<>();
    }
}
