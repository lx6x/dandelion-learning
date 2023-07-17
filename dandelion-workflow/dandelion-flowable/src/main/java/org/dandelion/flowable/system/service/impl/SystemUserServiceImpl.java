package org.dandelion.flowable.system.service.impl;

import org.dandelion.flowable.system.model.entity.SystemUserDO;
import org.dandelion.flowable.system.mapper.SystemUserMapper;
import org.dandelion.flowable.system.service.ISystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author lx6x
 * @since 2023/07/14
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUserDO> implements ISystemUserService {

}
