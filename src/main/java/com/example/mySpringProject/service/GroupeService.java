package com.example.mySpringProject.service;

import com.example.mySpringProject.dto.GroupeDto;

import java.util.List;

public interface GroupeService {

    GroupeDto createGroupe(GroupeDto groupeDto);
    List<GroupeDto> getAllGroupes();

    //test

}
