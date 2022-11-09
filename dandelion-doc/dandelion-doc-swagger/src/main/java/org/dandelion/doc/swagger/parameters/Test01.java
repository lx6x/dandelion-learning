package org.dandelion.doc.swagger.parameters;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.dandelion.doc.swagger.config.valid.ValidGroup;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/10/19 17:34
 */
@Api("Test01")
public class Test01 {

    /**
     * null 允许接口下该参数为空
     */
    @Null(groups = ValidGroup.Crud.Update.class)
    @NotNull(groups = ValidGroup.Crud.Query.class, message = "id不能为null")
    @ApiModelProperty(value = "id")
    private String id;


    @NotNull(groups = ValidGroup.Crud.Update.class)
    @ApiModelProperty(value = "名称")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
