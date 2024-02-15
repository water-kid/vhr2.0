package com.cj.framework.mapper;

import com.cj.framework.entity.Hr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.framework.entity.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author waterkid
 * @since 2024-02-12
 */
public interface HrMapper extends BaseMapper<Hr> {

    List<Role> getRolesByHrId(Integer id);
}
