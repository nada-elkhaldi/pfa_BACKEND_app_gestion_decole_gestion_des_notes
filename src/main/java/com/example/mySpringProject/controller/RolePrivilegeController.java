package com.example.mySpringProject.controller;


import com.example.mySpringProject.model.Privilege;
import com.example.mySpringProject.model.Role;
import com.example.mySpringProject.service.PrivilegeService;
import com.example.mySpringProject.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(value= "/api8",  method = {RequestMethod.POST,RequestMethod.GET,  RequestMethod.OPTIONS})
public class RolePrivilegeController {

    private final RoleService roleService;
    private final PrivilegeService privilegeService;

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/addPrivilege")
    public ResponseEntity<Privilege> createPrivilege(@RequestBody Privilege privilege) {
        Privilege createdPrivilege = privilegeService.createPrivilege(privilege);
        return new ResponseEntity<>(createdPrivilege, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/privileges")
    public ResponseEntity<List<Privilege>> getAllPrivileges() {
        List<Privilege> privileges = privilegeService.getAllPrivileges();
        return new ResponseEntity<>(privileges, HttpStatus.OK);
    }


    //
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/addRole")
    public ResponseEntity<Role> createRole(@RequestBody Role request) {
        Role createdRole = roleService.createRole(request);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/add-privileges-to-role")
    public ResponseEntity<Role> addPrivilegesToRole(
            @RequestParam Integer roleId,
            @RequestParam String privilegeIds)
    {
        try {

            List<Integer> privilegeIdList = Arrays.stream(privilegeIds.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            Role updatedRole = roleService.addPrivilegesToRole(roleId, privilegeIdList);
            return new ResponseEntity<>(updatedRole, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PutMapping("/updateRole/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable("id") Integer id, @RequestBody Role updatedRole) {
        Role role= roleService.updateRole(updatedRole,id);
        return ResponseEntity.ok(role);
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @DeleteMapping("/deleteRole/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Role deleted");

    }

}
