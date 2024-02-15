package com.cj.framework.entity.vo;

import com.cj.framework.entity.Menu;

import java.util.List;

public class MenuVo extends Menu {
    private List<MenuVo> children;

    public List<MenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVo> children) {
        this.children = children;
    }
}
