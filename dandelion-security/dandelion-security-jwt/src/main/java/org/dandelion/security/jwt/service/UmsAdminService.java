package org.dandelion.security.jwt.service;

import org.dandelion.security.jwt.entity.UmsAdmin;
import org.dandelion.security.jwt.entity.UmsPermission;

import java.util.List;

/**
 * TODO 后台管理员Service
 *
 * @author L
 * @version 1.0
 * @date 2022/3/10 18:00
 */
public interface UmsAdminService {

    /**
     * 根据用户名获取后台管理员
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册功能
     */
    UmsAdmin register(UmsAdmin umsAdminParam);

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     *
     * @param adminId 用户id
     * @return list
     * @author L
     */
    List<UmsPermission> getPermissionList(Long adminId);
}
