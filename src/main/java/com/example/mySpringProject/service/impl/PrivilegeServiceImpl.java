package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.model.Privilege;
import com.example.mySpringProject.repository.PrivilegeRepository;
import com.example.mySpringProject.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    @Autowired
    public PrivilegeServiceImpl(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Privilege createPrivilege(Privilege request) {
        Privilege privilege = new Privilege();
        privilege.setName(request.getName());
        return privilegeRepository.save(privilege);
    }

    @Override
    public Privilege findPrivilegeByName(Privilege name) {
        return null;
    }

    @Override
    public List<Privilege> getAllPrivileges() {
        return privilegeRepository.findAll();
    }
}
