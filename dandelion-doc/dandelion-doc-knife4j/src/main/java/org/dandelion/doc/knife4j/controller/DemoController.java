package org.dandelion.doc.knife4j.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dandelion.doc.knife4j.model.SchemaModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2023/6/20
 */
@Tag(name = "demo 测试")
@RestController
@RequestMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_VALUE)
public class DemoController {
    private final SchemaModel demoModel1 = new SchemaModel();
    private final SchemaModel demoModel2 = new SchemaModel();

    {
        demoModel1.setId(100L);
        demoModel1.setName("李四");

        demoModel2.setId(200L);
        demoModel2.setName("王二");
    }

    @Operation(summary = "根据类型获取", description = "根据类型获取")
    @GetMapping("/get")
    public SchemaModel get(int type) {
        return 1 == type ? demoModel1 : demoModel2;
    }

    @Operation(summary = "根据 Schema 获取",description = "根据 Schema 获取")
    @GetMapping("/getSchemaModel")
    public SchemaModel getDemo(SchemaModel schemaModel) {
        return schemaModel;
    }
}
