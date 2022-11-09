package org.dandelion.security.jwt.auth.controller;

import org.dandelion.security.jwt.auth.common.CommonResult;
import org.dandelion.security.jwt.auth.dto.UmsAdminLoginParam;
import org.dandelion.security.jwt.auth.entity.UmsAdmin;
import org.dandelion.security.jwt.auth.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 后台用户管理
 *
 * @author L
 * @version 1.0
 * @date 2022/3/14 15:41
 */
@RestController
@RequestMapping("/admin")
public class UmsAdminController {

    @Autowired
    private UmsAdminService adminService;

    @Value("${jwt.token-head}")
    private String tokenHead;


    /**
     * 用户注册
     *
     * @param umsAdminParam 用户注册信息
     * @return 用户已注册信息
     * @author L
     */
    @PostMapping(value = "/register")
    public CommonResult<UmsAdmin> register(@RequestBody UmsAdmin umsAdminParam, BindingResult result) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            CommonResult.failed();
        }
        return CommonResult.success(umsAdmin);
    }

    /**
     * 登录以后返回token
     *
     * @param umsAdminLoginParam 用户登录参数
     * @return common
     * @author L
     */
    @PostMapping(value = "/login")
    public CommonResult<Map<String, String>> login(@RequestBody UmsAdminLoginParam umsAdminLoginParam, BindingResult result) {
        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>(16);
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    /**
     * 验证token是否有效
     *
     * @param map {"token":"xxx","username":"登录用户名"}
     * @return boolean
     * @author L
     */
    @PostMapping(value = "/verifyToken")
    public CommonResult<Boolean> verifyToken(@RequestBody Map<String,String> map) {
        return CommonResult.success(adminService.isTokenExpired(map.get("token"),map.get("username")));
    }


}
