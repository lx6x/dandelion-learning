package org.dandelion.flowable.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dandelion.flowable.system.domain.SystemUsers;
import org.dandelion.flowable.system.service.SystemUsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lx6x
 * @date 2023/6/26
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/system/users")
public class SystemUsersController {

    @Resource
    private SystemUsersService usersService;

    @Operation(summary = "根据id获取用户信息")
    @GetMapping("/getUsers/{id}")
    public SystemUsers getUsers(@Parameter(name = "id",description = "用户id",in = ParameterIn.PATH) @PathVariable Long id) {
        return usersService.getById(id);
    }
}
