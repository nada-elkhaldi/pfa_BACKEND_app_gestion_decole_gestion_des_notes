package com.example.mySpringProject.controller;


import com.example.mySpringProject.model.Privilege;
import com.example.mySpringProject.model.Role;
import com.example.mySpringProject.service.PrivilegeService;
import com.example.mySpringProject.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value= "/api8",  method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})
public class RolePrivilegeController {

    private final RoleService roleService;
    private final PrivilegeService privilegeService;

    @PostMapping("/addPrivilege")
    public ResponseEntity<Privilege> createPrivilege(@RequestBody Privilege privilege) {
        Privilege createdPrivilege = privilegeService.createPrivilege(privilege);
        return new ResponseEntity<>(createdPrivilege, HttpStatus.CREATED);
    }

    @GetMapping("privileges")
    public ResponseEntity<List<Privilege>> getAllPrivileges() {
        List<Privilege> privileges = privilegeService.getAllPrivileges();
        return new ResponseEntity<>(privileges, HttpStatus.OK);
    }


    //
    @PostMapping("/addRole")
    public ResponseEntity<Role> createRole(@RequestBody Role request) {
        Role createdRole = roleService.createRole(request);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping("/add-privileges-to-role")
    public ResponseEntity<Role> addPrivilegesToRole(
            @RequestParam Integer roleId,
            @RequestParam List<Integer> privilegeIds) {
        try {
            Role updatedRole = roleService.addPrivilegesToRole(roleId, privilegeIds);
            return new ResponseEntity<>(updatedRole, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
