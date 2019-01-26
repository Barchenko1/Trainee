package com.nixsolutions.barchenko.service;

import com.nixsolutions.barchenko.entity.Role;

import java.util.List;

public interface RoleService {
    void create(Role role);

    void update(Role role);

    void remove(Role role);

    Role findByName(String name);

    List<Role> findAll();

    Role findById(Long id);
}
