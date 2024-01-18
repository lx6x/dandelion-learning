package org.dandelion.sa.token.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lx6x
 * @date 2024/1/18
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    /**
     * 测试登录，浏览器访问： <a href="http://localhost:30048/user/doLogin?username=zhang&password=123456">点击请求</a>
     */
    @GetMapping("doLogin")
    public SaResult doLogin(String username, String password) {

        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if ("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return SaResult.data(tokenInfo);
        }
        return SaResult.error("登录失败");
    }

    /**
     * 查询登录状态，浏览器访问： <a href="http://localhost:30048/user/isLogin">点击请求</a>
     */
    @GetMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }

    /**
     * 查询 Token 信息  ---- <a href="http://localhost:30048/user/tokenInfo">点击请求</a>
     */
    @RequestMapping("tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    /**
     * 注销  ---- <a href="http://localhost:30048/user/logout">点击请求</a>
     */
    @RequestMapping("logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

}