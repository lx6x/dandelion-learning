package org.dandelion.excel.poi;

import java.time.LocalDateTime;


public class TPartnerCertificate {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String id;

    /**
     * 业务id(相关方机构id/相关方人员id)
     */
    private String bizId;

    /**
     * 资质名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 资质使用类型 1-单位资质证书；2-人员资质
     */
    private Integer useType;
    /**
     * 有效期
     */
    private LocalDateTime validStartDate;
    /**
     * 有效期（复审日期）
     */
    private String validEndDate;
    /**
     * 路径
     */
    private String refId;
    /**
     * 资质状态（0-过期失效，1-正常）
     */
    private String state;
    /**
     * 租户id
     */
    private String tenantId;

    private Integer type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
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

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    public LocalDateTime getValidStartDate() {
        return validStartDate;
    }

    public void setValidStartDate(LocalDateTime validStartDate) {
        this.validStartDate = validStartDate;
    }

    public String getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(String validEndDate) {
        this.validEndDate = validEndDate;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
