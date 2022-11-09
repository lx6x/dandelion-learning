package org.dandelion.excel.poi;

import java.util.List;

/**
 * 相关方机构员工对象 t_partner_staff
 *
 * @author testor-framework
 * @date 2022-06-01 14:18:06
 */
public class TPartnerStaff {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String id;
    /**
     * 组织机构id
     */
    private String orgId;
    /**
     * 相关方机构id
     */
    private String partnerId;

    private String name;
    /**
     * 0-女 1-男
     */
    private Integer sex;

    private String workType;

    private String trainingStatus;

    /**
     * 身份证
     */
    private String idcard;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 资质状态（0-异常，1-正常）
     */
    private Integer certificateStatus;
    /**
     * 年龄(计算库中不存)
     */
    private Integer age;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 登录用户id
     */
    private String sysUserId;

    private List<TPartnerCertificate> items;

    public List<TPartnerCertificate> getItems() {
        return items;
    }

    public void setItems(List<TPartnerCertificate> items) {
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(String trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCertificateStatus() {
        return certificateStatus;
    }

    public void setCertificateStatus(Integer certificateStatus) {
        this.certificateStatus = certificateStatus;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

}
