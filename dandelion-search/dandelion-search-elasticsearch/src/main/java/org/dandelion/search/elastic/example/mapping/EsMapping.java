package org.dandelion.search.elastic.example.mapping;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author lx6x
 * @date 2023/8/2
 */
@Data
@Document(indexName = "esMapping")
public class EsMapping {

    @Field(type = FieldType.Keyword)
    private Long id;
    @Field(type = FieldType.Keyword)
    private String userName;
    @Field(type = FieldType.Keyword)
    private Integer age;

}
