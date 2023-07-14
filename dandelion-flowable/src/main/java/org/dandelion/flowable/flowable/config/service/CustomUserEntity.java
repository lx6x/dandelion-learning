package org.dandelion.flowable.flowable.config.service;

import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;

/**
 * @author liujunfei
 * @date 2023/7/14
 */
public class CustomUserEntity extends UserEntityImpl {

    public CustomUserEntity(User user) {
        super();
    }
}
