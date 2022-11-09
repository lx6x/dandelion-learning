package org.dandelion.security.jwt.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dandelion.security.jwt.auth.dao.UmsAdminRoleRelationDao;
import org.dandelion.security.jwt.auth.dto.AdminUserDetails;
import org.dandelion.security.jwt.auth.entity.UmsAdmin;
import org.dandelion.security.jwt.auth.entity.UmsPermission;
import org.dandelion.security.jwt.auth.mapper.UmsAdminMapper;
import org.dandelion.security.jwt.auth.service.UmsAdminService;
import org.dandelion.security.jwt.auth.utils.DateUtils;
import org.dandelion.security.jwt.auth.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author L
 * @version 1.0
 * @date 2022/3/10 18:02
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UmsAdmin getAdminByUsername(String username) {
        // 默认 用户名称 只能存在一个
        return umsAdminMapper.selectOne(new QueryWrapper<UmsAdmin>().eq("username", username));
    }

    @Override
    public UmsAdmin register(UmsAdmin umsAdminParam) {
        umsAdminParam.setCreateTime(DateUtils.getNowDate());
        umsAdminParam.setStatus(1);
        QueryWrapper<UmsAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", umsAdminParam.getUsername());
        UmsAdmin umsAdmin = umsAdminMapper.selectOne(queryWrapper);
        if (null != umsAdmin) {
            return null;
        }
        String password = umsAdminParam.getPassword();
        String encode = passwordEncoder.encode(password);

        LOGGER.info("原密码：{}，加密后密码：{}", password, encode);
        umsAdminParam.setPassword(encode);
        umsAdminMapper.insert(umsAdminParam);
        return umsAdminParam;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            // 实际调用 WebSecurityConfig --> userDetailsService 方法
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // 验证密码是否正确
            String userDetailsPassword = userDetails.getPassword();
            boolean matches = passwordEncoder.matches(password, userDetailsPassword);
            if (!matches) {
                // org.springframework.security.authentication.BadCredentialsException 使用 security 默认给出异常抛出类
                throw new BadCredentialsException("密码不正确");
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }

        return token;
    }

    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return umsAdminRoleRelationDao.getPermissionList(adminId);
    }

    @Override
    public boolean isTokenExpired(String token,String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.validateToken(token,userDetails);
    }
}
