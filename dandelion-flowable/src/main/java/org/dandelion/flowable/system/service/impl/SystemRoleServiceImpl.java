package org.dandelion.flowable.system.service.impl;

import org.dandelion.flowable.system.domain.SystemRole;
import org.dandelion.flowable.system.mapper.SystemRoleMapper;
import org.dandelion.flowable.system.service.ISystemRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author lx6x
 * @since 2023/07/14
 */
@Service
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRole> implements ISystemRoleService {

}
