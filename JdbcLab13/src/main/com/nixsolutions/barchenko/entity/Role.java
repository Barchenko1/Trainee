package com.nixsolutions.barchenko.entity;

import java.util.Objects;

public class Role {

    private Long roleId;
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(Long roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" + "roleId=" + roleId + ", name='" + name + '\'' + '}';
    }

}
