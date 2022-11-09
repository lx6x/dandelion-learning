package org.dandelion.data.routing.source.controller;

import org.dandelion.data.routing.source.datasource.DynamicDataSource;
import org.dandelion.data.routing.source.datasource.DynamicDataSourceContextHolder;
import org.dandelion.data.routing.source.entity.DUserDel;
import org.dandelion.data.routing.source.service.DUserDelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/6/8 16:35
 */
@RestController
@RequestMapping("/router/data/source")
public class DUserDelController {

    private DynamicDataSource dynamicDataSource;

    public DUserDelController() {
        this.dynamicDataSource = DynamicDataSource.getHungryManSingleton();
    }

    @Autowired
    private DUserDelService dUserDelService;

    @RequestMapping("/master")
    public List<DUserDel> master() {
        return dUserDelService.master();
    }

    @RequestMapping("/slave")
    public List<DUserDel> slave() {
        return dUserDelService.slave();
    }

    @RequestMapping("/def")
    public List<DUserDel> def() {
        return dUserDelService.def();
    }

    @GetMapping("/toggleDataSource")
    public void toggleDataSource(@RequestParam String dataSourceName, HttpSession session) {
        session.setAttribute("session_key",dataSourceName);
    }

}
