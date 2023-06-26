package org.dandelion.flowable.flowable.config.service;

import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.idm.api.Group;
import org.flowable.idm.engine.impl.GroupQueryImpl;

import java.util.List;

/**
 * 管理组信息
 *
 * @author lx6x
 * @date 2023/6/26
 */
public class FlowableGroupQueryImpl extends GroupQueryImpl {

    @Override
    public long executeCount(CommandContext commandContext) {
        return super.executeCount(commandContext);
    }

    @Override
    public List<Group> executeList(CommandContext commandContext) {
        return super.executeList(commandContext);
    }
}
