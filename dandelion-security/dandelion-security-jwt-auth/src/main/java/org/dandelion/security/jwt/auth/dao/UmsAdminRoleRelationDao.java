package org.dandelion.security.jwt.auth.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dandelion.security.jwt.auth.entity.UmsPermission;

import java.util.List;

/**
 * TODO 后台用户与角色管理自定义Dao
 *
 * @author L
 * @version 1.0
 * @date 2022/3/10 18:27
 */
@Mapper
public interface UmsAdminRoleRelationDao {

    /**
     * 获取用户所有权限(包括+-权限)
     */
    List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);
}
