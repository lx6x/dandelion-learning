package org.dandelion.ureport2.entity;

/**
 * @author lx6x
 * @date 2023/9/19
 */
public class User {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }
}
