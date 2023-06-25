package org.dandelion.doc.knife4j.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户
 *
 * @date 2023/6/20
 */
@Schema(description = "SchemaModel 测试")
public class SchemaModel {

    @Schema(description = "id",defaultValue = "1")
    private Long id;

    @Schema(description = "名称",defaultValue = "张三")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
