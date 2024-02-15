package com.cj.framework.service.impl;

import com.cj.framework.entity.Hr;
import com.cj.framework.entity.Menu;
import com.cj.framework.entity.vo.MenuVo;
import com.cj.framework.entity.vo.MenuWithRole;
import com.cj.framework.mapper.MenuMapper;
import com.cj.framework.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    MenuMapper menuMapper;
    @Override
    public List<MenuVo> getMenusByHrId() {
        Hr principal = (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return menuMapper.getMenusByHrId(principal.getId());
    }


    @Override
    public List<MenuWithRole> getAllMenusWithRole() {
        return menuMapper.getAllMenusWithRole();
    }
}
