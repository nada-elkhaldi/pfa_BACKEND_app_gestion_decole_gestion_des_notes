package com.example.mySpringProject.mapper;

import com.example.mySpringProject.dto.GroupeDto;
import com.example.mySpringProject.model.Groupe;
import org.springframework.stereotype.Component;


@Component
public class GroupeMapper {

    public static GroupeDto mapToGroupDto(Groupe groupe) {
        return new GroupeDto(
                groupe.getId(),
                groupe.getGroupName()
        );

    }

    public static Groupe mapToGroupe(GroupeDto groupeDto) {
        return new Groupe(
               groupeDto.getId(),
                groupeDto.getGroupName()
        );
    }
}
