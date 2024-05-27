package com.example.mySpringProject.service;

import com.example.mySpringProject.dto.GroupeDto;
import com.example.mySpringProject.model.Groupe;

import java.util.List;

public interface GroupeService {

    GroupeDto createGroupe(GroupeDto groupeDto);
    List<GroupeDto> getAllGroupes();
    Groupe getGroupeById(int id);


}
