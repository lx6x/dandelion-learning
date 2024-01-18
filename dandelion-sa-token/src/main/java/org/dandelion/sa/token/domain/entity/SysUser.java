package org.dandelion.sa.token.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author lx6x
 * @since 2024/01/18
 */
@Getter
@Setter
@TableName("sys_user")
@Schema(name = "SysUser", description = "用户")
public class SysUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "姓名")
    private String realName;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户密码")
    private String password;

    @Schema(description = "密码加盐")
    private String salt;

    @Schema(description = "手机号")
    private String mobilePhone;

    @Schema(description = "联系电话")
    private String tel;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "管理员类型<sys_admin_type>")
    private Integer adminType;

    @Schema(description = "性别<sys_sex>")
    private Integer sex;

    @Schema(description = "是否锁定")
    private Boolean isLocked;

    @Schema(description = "所属部门")
    private Long deptId;

    @Schema(description = "所属岗位")
    private Long postId;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建用户")
    private Long createUser;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "更新用户")
    private Long updateUser;

    @Schema(description = "是否删除")
    private Boolean isDeleted;
}
