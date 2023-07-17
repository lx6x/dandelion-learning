package org.dandelion.flowable.system.service.impl;

import org.dandelion.flowable.system.model.entity.SystemUserRoleDO;
import org.dandelion.flowable.system.mapper.SystemUserRoleMapper;
import org.dandelion.flowable.system.service.ISystemUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author lx6x
 * @since 2023/07/14
 */
@Service
public class SystemUserRoleServiceImpl extends ServiceImpl<SystemUserRoleMapper, SystemUserRoleDO> implements ISystemUserRoleService {

}
