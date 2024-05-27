package com.example.mySpringProject.service.impl;


import com.example.mySpringProject.dto.GroupeDto;
import com.example.mySpringProject.mapper.GroupeMapper;
import com.example.mySpringProject.model.Groupe;
import com.example.mySpringProject.repository.GroupeRepository;
import com.example.mySpringProject.service.GroupeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupeServiceImpl implements GroupeService {
    private GroupeRepository groupeRepository;
    private GroupeMapper groupeMapper;

    @Override
    public GroupeDto createGroupe(GroupeDto groupeDto) {
        Groupe groupe = GroupeMapper.mapToGroupe(groupeDto);
        Groupe savedGroupe=groupeRepository.save(groupe);
        return groupeMapper.mapToGroupDto(savedGroupe);
    }

    @Override
    public List<GroupeDto> getAllGroupes() {
         List<Groupe> groupes = groupeRepository.findAll();
         return groupes.stream().map((groupe)-> groupeMapper.mapToGroupDto(groupe))
                 .collect(Collectors.toList());
    }

    @Override
    public Groupe getGroupeById(int id) {
        Groupe groupe = groupeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Groupe not found with id: " + id));
        return groupe;
    }
}
