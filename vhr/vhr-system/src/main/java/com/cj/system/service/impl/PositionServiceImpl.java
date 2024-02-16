package com.cj.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cj.framework.entity.RespPageBean;
import com.cj.system.entity.Position;
import com.cj.system.mapper.PositionMapper;
import com.cj.system.service.IPositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author waterkid
 * @since 2024-02-15
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

    @Override
    public RespPageBean getPositionsByPage(Integer page, Integer size) {
//        page()

//        Page.of()
        Page<Position> pager = new Page<>(page, size);


        Page<Position> result = this.page(pager);

        List<Position> records = result.getRecords();
        long total = result.getTotal();

        return new RespPageBean(total,records);
    }
}
