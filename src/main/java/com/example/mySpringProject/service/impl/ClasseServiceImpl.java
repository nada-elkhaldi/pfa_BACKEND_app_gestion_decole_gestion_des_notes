package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.dto.ClasseDto;
import com.example.mySpringProject.mapper.ClasseMapper;
import com.example.mySpringProject.model.Classe;
import com.example.mySpringProject.repository.ClasseRepository;
import com.example.mySpringProject.service.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClasseServiceImpl implements ClasseService {
    private ClasseRepository classeRepository;
    private ClasseMapper classeMapper;


    @Autowired
    public ClasseServiceImpl(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }
    @Override
    public List<ClasseDto> createClasse(List<ClasseDto> classeDtoList) {
        List<Classe> savedClasses = new ArrayList<>();
        for (ClasseDto request : classeDtoList) {
            Classe classe = ClasseMapper.mapToClasse(request);
            Classe savedClasse = classeRepository.save(classe);
            savedClasses.add(savedClasse);
        }
        List<ClasseDto> savedClasseDtos = ClasseMapper.mapToClasseDtoList(savedClasses);
        return savedClasseDtos;
    }

    @Override
    public List<ClasseDto> getAllClasses() {
        List<Classe> classes = classeRepository.findAll();
        return classes.stream().map((classe)-> classeMapper.mapToClasseDto(classe))
                .collect(Collectors.toList());
    }
}
