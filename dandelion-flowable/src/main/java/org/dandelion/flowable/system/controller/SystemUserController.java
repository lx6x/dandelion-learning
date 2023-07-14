package org.dandelion.flowable.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dandelion.flowable.system.domain.SystemUser;
import org.dandelion.flowable.system.service.ISystemUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author lx6x
 * @since 2023/07/14
 */
@Tag(name = "用户信息")
@RestController
@RequestMapping("/systemUser")
public class SystemUserController {

    @Resource
    private ISystemUserService iSystemUserService;

    @Operation(summary = "根据id获取用户信息")
    @GetMapping("/get/{id}")
    @Parameters({
            @Parameter(name = "id", description = "用户主键id",in = ParameterIn.PATH)
    })
    public SystemUser get(@PathVariable("id") Long id) {
        return iSystemUserService.getById(id);
    }

}
