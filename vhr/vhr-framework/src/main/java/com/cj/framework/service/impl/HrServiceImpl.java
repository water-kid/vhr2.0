package com.cj.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.framework.entity.Hr;
import com.cj.framework.entity.Role;
import com.cj.framework.mapper.HrMapper;
import com.cj.framework.service.IHrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author waterkid
 * @since 2024-02-12
 */
@Service
public class HrServiceImpl extends ServiceImpl<HrMapper, Hr> implements IHrService {

    @Autowired
    HrMapper hrMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Hr> qw = new QueryWrapper<>();
        qw.lambda().eq(Hr::getUsername,username);
        Hr hr = this.getOne(qw);
        if (hr == null){
            throw new UsernameNotFoundException("用户不存在，登录失败");
        }

        // 查询权限
        List<Role> roleList = hrMapper.getRolesByHrId(hr.getId());

        hr.setRoles(roleList);

        return hr;
    }
}
