package com.nixsolutions.barchenko.service.impl;

import com.nixsolutions.barchenko.dao.RoleDao;
import com.nixsolutions.barchenko.entity.Role;
import com.nixsolutions.barchenko.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Service
@EnableTransactionManagement
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Transactional
    public void create(Role role) {
        roleDao.create(role);
    }

    @Transactional
    public void update(Role role) {
        roleDao.update(role);
    }

    @Transactional
    public void remove(Role role) {
        roleDao.remove(role);
    }

    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleDao.findAll();

    }

    @Transactional(readOnly = true)
    public Role findById(Long id) {
        return roleDao.findById(id);
    }

    @Transactional
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }
}
