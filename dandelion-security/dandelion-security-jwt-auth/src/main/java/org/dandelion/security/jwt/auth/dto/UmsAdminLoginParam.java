package org.dandelion.security.jwt.auth.dto;

/**
 * TODO 用户登录参数
 *
 * @author L
 * @version 1.0
 * @date 2022/3/15 14:45
 */
public class UmsAdminLoginParam {

    /**
     * 登录用户名
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "UmsAdminLoginParam{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
