package com.cj.framework.mapper;

import com.cj.framework.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.framework.entity.vo.MenuVo;
import com.cj.framework.entity.vo.MenuWithRole;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author waterkid
 * @since 2024-02-12
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<MenuVo> getMenusByHrId(Integer id);

    List<MenuWithRole> getAllMenusWithRole();
}
