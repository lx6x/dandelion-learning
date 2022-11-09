package org.dandelion.jpa.entity;

import javax.persistence.*;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/3/2 17:49
 */
@Entity  // 声明实体
@Table(name = "user")  // 声明表
public class User {

    @Id // 指定属性类别，标识主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 主键生成策略
    private Integer id;

    private String name;

    private Integer age;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
