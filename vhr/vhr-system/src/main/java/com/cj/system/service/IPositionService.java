package com.cj.system.service;

import com.cj.framework.entity.RespPageBean;
import com.cj.system.entity.Position;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author waterkid
 * @since 2024-02-15
 */
public interface IPositionService extends IService<Position> {

    RespPageBean getPositionsByPage(Integer page, Integer size);

}
