package org.dandelion.easyexcel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author lx6x
 * @since 2023/08/09
 */
@Data
@Schema(name = "User", description = "用户信息")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "性别")
    private String sex;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "创建时间")
    private LocalDateTime createDate;
}
