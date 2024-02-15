package com.cj.framework.service;

import com.cj.framework.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cj.framework.entity.vo.MenuVo;
import com.cj.framework.entity.vo.MenuWithRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author waterkid
 * @since 2024-02-12
 */
public interface IMenuService extends IService<Menu> {

    List<MenuVo> getMenusByHrId();

    /**
     * 获取所有的menu  和 每个menu需要的roles
     * @return
     */
    List<MenuWithRole> getAllMenusWithRole();

}
