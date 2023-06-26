package org.dandelion.flowable.flowable.config.service;

import org.flowable.idm.api.GroupQuery;
import org.flowable.idm.api.UserQuery;
import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.impl.IdmIdentityServiceImpl;

/**
 * 扩展flowable用户权限
 *
 * @author lx6x
 * @date 2023/6/26
 */
public class FlowableIdentityServiceImpl extends IdmIdentityServiceImpl {
    public FlowableIdentityServiceImpl(IdmEngineConfiguration idmEngineConfiguration) {
        super(idmEngineConfiguration);
    }

    @Override
    public UserQuery createUserQuery() {
        return new FlowableUserQueryImpl();
    }

    @Override
    public GroupQuery createGroupQuery() {
        return new FlowableGroupQueryImpl();
    }
}
