package org.dandelion.flowable.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 *
 * @TableName system_users
 */
@TableName(value = "system_users")
public class SystemUsers implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 备注
     */
    private String remark;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 岗位编号数组
     */
    private String postIds;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户性别
     */
    private Integer sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updater;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 租户编号
     */
    private Long tenantId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 用户ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 用户账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户账号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 用户昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 用户昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 部门ID
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * 部门ID
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * 岗位编号数组
     */
    public String getPostIds() {
        return postIds;
    }

    /**
     * 岗位编号数组
     */
    public void setPostIds(String postIds) {
        this.postIds = postIds;
    }

    /**
     * 用户邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 用户邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 用户性别
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 用户性别
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 头像地址
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 头像地址
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 帐号状态（0正常 1停用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 帐号状态（0正常 1停用）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 最后登录IP
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 最后登录IP
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * 最后登录时间
     */
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     * 最后登录时间
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * 创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建者
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新者
     */
    public String getUpdater() {
        return updater;
    }

    /**
     * 更新者
     */
    public void setUpdater(String updater) {
        this.updater = updater;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 是否删除
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * 是否删除
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * 租户编号
     */
    public Long getTenantId() {
        return tenantId;
    }

    /**
     * 租户编号
     */
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

}