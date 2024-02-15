package com.cj.controller;

import com.cj.framework.entity.RespBean;
import com.cj.framework.entity.vo.MenuVo;
import com.cj.framework.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {
    @Autowired
    IMenuService menuService;

    @GetMapping("/menus")
    public RespBean getMenusByHrId(){
        List<MenuVo> menus = menuService.getMenusByHrId();

        return (menus!=null&&menus.size()>0)?RespBean.ok("获取成功",menus):RespBean.error("没有列表");
    }
}
