package org.dandelion.scheduling.xxljob.controller;

import org.dandelion.commons.model.CommonResult;
import org.dandelion.commons.model.annotation.PermissionLimit;
import org.dandelion.scheduling.xxljob.feign.XxlJobAdminFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO mockMvc请求测试
 *
 * @author L
 * @version 1.0
 * @date 2021/10/14 16:43
 */
@RestController
@RequestMapping("/xxl")
public class XxlJobController {

    @Autowired
    private XxlJobAdminFeign xxlJobAdminFeign;

    /**
     * xxl-job列表信息
     *
     * @return Result
     * @author L
     */
    @PermissionLimit
    @GetMapping("/pageList")
    private CommonResult<String> pageList() {
        String pageList = xxlJobAdminFeign.pageList(0, 10, 3, -1, "", "", "");
        // 返回未处理
        return CommonResult.success(pageList);
    }


}
