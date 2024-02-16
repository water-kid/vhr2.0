package com.cj.controller.system;

import com.cj.framework.entity.RespBean;
import com.cj.framework.entity.RespPageBean;
import com.cj.system.entity.Position;
import com.cj.system.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/basic/position")
public class PositionController {

    @Autowired
    IPositionService positionService;

    @GetMapping
    public RespPageBean getPositionByPage(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "2") Integer size){

        return positionService.getPositionsByPage(page,size);
    }


    @PutMapping
    public RespBean updatePositionById(@RequestBody Position position){
        return positionService.updateById(position)?RespBean.ok("更新成功"):RespBean.error("更新失败");
    }

}
