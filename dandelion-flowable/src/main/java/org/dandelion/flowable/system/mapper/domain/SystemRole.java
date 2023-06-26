package org.dandelion.flowable.system.mapper.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色信息表
 *
 * @TableName system_role
 */
@Schema(description = "角色信息表")
@TableName(value = "system_role")
public class SystemRole implements Serializable {
    /**
     * 角色ID
     */
    @SchemaProperty(name = "角色ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色权限字符串
     */
    private String code;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    private Integer dataScope;

    /**
     * 数据范围(指定部门数组)
     */
    private String dataScopeDeptIds;

    /**
     * 角色状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 角色类型
     */
    private Integer type;

    /**
     * 备注
     */
    private String remark;

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
     * 角色ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 角色ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 角色权限字符串
     */
    public String getCode() {
        return code;
    }

    /**
     * 角色权限字符串
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 显示顺序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 显示顺序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    public Integer getDataScope() {
        return dataScope;
    }

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    public void setDataScope(Integer dataScope) {
        this.dataScope = dataScope;
    }

    /**
     * 数据范围(指定部门数组)
     */
    public String getDataScopeDeptIds() {
        return dataScopeDeptIds;
    }

    /**
     * 数据范围(指定部门数组)
     */
    public void setDataScopeDeptIds(String dataScopeDeptIds) {
        this.dataScopeDeptIds = dataScopeDeptIds;
    }

    /**
     * 角色状态（0正常 1停用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 角色状态（0正常 1停用）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 角色类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 角色类型
     */
    public void setType(Integer type) {
        this.type = type;
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