package org.dandelion.mybatis.flex.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

/**
 * @author lx6x
 * @date 2023/12/13
 */
@Data
@Table("user")
public class User {

    @Id(keyType = KeyType.Auto)
    private int id;
    private String name;
    private String sex;
    private int age;
    private Date addDate;
}
