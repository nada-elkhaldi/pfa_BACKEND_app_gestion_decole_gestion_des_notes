package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.exception.ResourceNotFoundException;
import com.example.mySpringProject.model.Privilege;
import com.example.mySpringProject.model.Region;
import com.example.mySpringProject.model.Role;
import com.example.mySpringProject.repository.PrivilegeRepository;
import com.example.mySpringProject.repository.RoleRepository;
import com.example.mySpringProject.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;

    }

    @Override
    public Role createRole(Role request) {
        Role role = new Role();
        role.setRoleName(request.getRoleName());
        return roleRepository.save(role);
    }

    @Override
    public Role findRoleByName(String name) {
        return null;
    }


    @Override
    public Role addPrivilegesToRole(Integer roleId, List<Integer> privilegeIds) {

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role with ID " + roleId + " does not exist."));


        List<Privilege> privileges = privilegeIds.stream()
                .map(id -> privilegeRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Privilege with ID " + id + " does not exist."))
                )
                .collect(Collectors.toList());

        role.setPrivileges(new HashSet<>(privileges));
        return roleRepository.save(role);
    }


    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role updateRole(Role request, Integer id) {
        Role role = roleRepository
                .findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Role with id " + id + " not found")
        );
        role.setRoleName(request.getRoleName());

        Role savedRole = roleRepository.save(role);
        return savedRole;
    }

    @Override
    public void deleteRole(Integer roleId) {

        Role role = roleRepository
                .findById(roleId).orElseThrow(
                        ()-> new ResourceNotFoundException("Role with id " + roleId + " not found")
                );

        roleRepository.deleteById(roleId);
    }
}
