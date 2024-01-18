package org.dandelion.sa.token.domain.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.dandelion.sa.token.domain.entity.SysUser;
import org.dandelion.sa.token.domain.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author lx6x
 * @since 2024/01/18
 */
@Service
public class SysUserDao extends ServiceImpl<SysUserMapper, SysUser> implements IService<SysUser> {

}
