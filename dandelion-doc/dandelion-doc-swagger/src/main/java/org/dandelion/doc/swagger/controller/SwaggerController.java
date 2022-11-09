package org.dandelion.doc.swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dandelion.doc.swagger.config.valid.ValidGroup;
import org.dandelion.doc.swagger.parameters.Test01;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2021/10/19 17:25
 */
@Api(tags = "Controller")
@RequestMapping("swaggers")
@RestController
public class SwaggerController {

    @ApiOperation("查询")
    @PostMapping("query")
    public String query(@Validated(value = {ValidGroup.Crud.Query.class}) @RequestBody Test01 test01) {
        System.out.println(test01.toString());
        return "ok";
    }

    @ApiOperation("修改")
    @PostMapping("update")
    public String update(@Validated(value = {ValidGroup.Crud.Update.class}) @RequestBody Test01 test01) {
        System.out.println(test01.toString());
        return "ok";
    }

}
