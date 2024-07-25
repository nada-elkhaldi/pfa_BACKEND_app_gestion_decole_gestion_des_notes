package com.example.mySpringProject.service;

import com.example.mySpringProject.model.Privilege;

import java.util.List;

public interface PrivilegeService {

    Privilege createPrivilege(Privilege name);
    Privilege findPrivilegeByName(Privilege name);

    List<Privilege> getAllPrivileges() ;
}
