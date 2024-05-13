package com.example.mySpringProject.service.impl;


import com.example.mySpringProject.dto.MatiereDto;
import com.example.mySpringProject.mapper.MatiereMapper;
import com.example.mySpringProject.model.Matiere;
import com.example.mySpringProject.repository.MatiereRepository;
import com.example.mySpringProject.service.MatiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatiereServiceImpl implements MatiereService {
    private MatiereRepository matiereRepository;
    private MatiereMapper matiereMapper;


    @Autowired
    public MatiereServiceImpl(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }

        @Override
    public List<MatiereDto> createMatiere(List<MatiereDto> matiereDto) {
        List<Matiere> savedMatieres = new ArrayList<>();
        for (MatiereDto request : matiereDto) {
            Matiere matiere = MatiereMapper.mapToMatiere(request);
            Matiere savedMatiere = matiereRepository.save(matiere);
            savedMatieres.add(savedMatiere);
        }
        List<MatiereDto> savedMatiereDtos = MatiereMapper.mapToMatiereDtoList(savedMatieres);
        return savedMatiereDtos;
    }

    @Override
    public List<MatiereDto> getAllMatieres() {
        List<Matiere> matieres = matiereRepository.findAll();
        return matieres.stream().map((matiere)-> matiereMapper.mapToMatiereDto(matiere))
                .collect(Collectors.toList());
    }
}
