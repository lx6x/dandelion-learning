package org.dandelion.flowable.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * 角色信息表
 * </p>
 *
 * @author lx6x
 * @since 2023/07/14
 */
@TableName("system_role")
@Schema(name = "SystemRole", description = "角色信息表")
public class SystemRoleDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "角色ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色权限字符串")
    private String code;

    @Schema(description = "显示顺序")
    private Integer sort;

    @Schema(description = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private Byte dataScope;

    @Schema(description = "数据范围(指定部门数组)")
    private String dataScopeDeptIds;

    @Schema(description = "角色状态（0正常 1停用）")
    private Byte status;

    @Schema(description = "角色类型")
    private Byte type;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建者")
    private String creator;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新者")
    private String updater;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "租户编号")
    private Long tenantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Byte getDataScope() {
        return dataScope;
    }

    public void setDataScope(Byte dataScope) {
        this.dataScope = dataScope;
    }

    public String getDataScopeDeptIds() {
        return dataScopeDeptIds;
    }

    public void setDataScopeDeptIds(String dataScopeDeptIds) {
        this.dataScopeDeptIds = dataScopeDeptIds;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "SystemRole{" +
        "id = " + id +
        ", name = " + name +
        ", code = " + code +
        ", sort = " + sort +
        ", dataScope = " + dataScope +
        ", dataScopeDeptIds = " + dataScopeDeptIds +
        ", status = " + status +
        ", type = " + type +
        ", remark = " + remark +
        ", creator = " + creator +
        ", createTime = " + createTime +
        ", updater = " + updater +
        ", updateTime = " + updateTime +
        ", deleted = " + deleted +
        ", tenantId = " + tenantId +
        "}";
    }
}
