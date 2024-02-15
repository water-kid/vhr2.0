package com.cj.framework.entity.vo;

import com.cj.framework.entity.Menu;
import com.cj.framework.entity.Role;

import java.util.List;

/**
 * 这个menu 什么角色可以访问
 */
public class MenuWithRole extends Menu {
    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
