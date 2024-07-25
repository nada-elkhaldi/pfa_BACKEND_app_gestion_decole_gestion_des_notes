package com.example.mySpringProject.service;

import com.example.mySpringProject.model.Role;

import java.util.List;

public interface RoleService {

    Role createRole(Role name);
    Role findRoleByName(String name);

    Role addPrivilegesToRole(Integer roleId, List<Integer> privilegeIds);

     List<Role> getAllRoles();
}
