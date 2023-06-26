package org.dandelion.flowable.flowable.config.service;

import cn.hutool.extra.spring.SpringUtil;
import org.dandelion.flowable.system.mapper.domain.SystemUsers;
import org.dandelion.flowable.system.service.SystemUsersService;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.UserQueryImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 管理用户信息
 * 重写 UserQueryImpl
 *
 * @author lx6x
 * @date 2023/6/26
 */
public class FlowableUserQueryImpl extends UserQueryImpl {

    private final SystemUsersService usersService;

    public FlowableUserQueryImpl() {
        this.usersService = SpringUtil.getBean(SystemUsersService.class);
    }

    @Override
    public long executeCount(CommandContext commandContext) {
        return super.executeCount(commandContext);
    }

    @Override
    public List<User> executeList(CommandContext commandContext) {
        List<User> resultList = new LinkedList<>();

        if (getId() != null) {
            List<User> result = new ArrayList<>();
            SystemUsers users = this.usersService.getById(getId());
        }

        // 登录验证
        if(getIdIgnoreCase()!=null){
            SystemUsers users = this.usersService.getById(getId());
        }

        return resultList;
    }

    /* @Override
    public long executeCount(CommandContext commandContext) {
        return executeQuery().size();
    }

    @Override
    public List<User> executeList(CommandContext commandContext) {
        return executeQuery();
    }

    protected List<User> executeQuery() {
        if (getId() != null) {
            List<User> result = new ArrayList<>();
            result.add(findById(getId()));
            return result;

        } else if (getIdIgnoreCase() != null) {
            List<User> result = new ArrayList<>();
            result.add(findById(getIdIgnoreCase()));
            return result;

        } else {
            return executeAllUserQuery();
        }
    }


    protected List<User> executeNameQuery(String name) {
        String fullName = name.replaceAll("%", "");
        return executeUsersQuery(fullName);
    }

    protected List<User> executeAllUserQuery() {
        return executeUsersQuery("");
    }

    private User findById(final String userId) {

        UserEntity userEntity = new UserEntityImpl();
        userEntity.setId(userId);
        userEntity.setDisplayName("张三");
        userEntity.setTenantId("1");
        return userEntity;
    }
    protected List<User> executeUsersQuery(String fullName) {
        return new ArrayList<>();
    }*/
}
