package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.dto.SemestreDto;
import com.example.mySpringProject.mapper.SemestreMapper;
import com.example.mySpringProject.model.Semestre;
import com.example.mySpringProject.repository.SemestreRepository;
import com.example.mySpringProject.service.SemestreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SemestreServiceImpl implements SemestreService {
    private SemestreRepository semestreRepository;
    private SemestreMapper semestreMapper;


    @Override
    public SemestreDto createSemestre(SemestreDto semestreDto) {
        Semestre semestre = SemestreMapper.mapToSemestre(semestreDto);
        Semestre savedSemestre=semestreRepository.save(semestre);
        return semestreMapper.mapToSemestreDto(savedSemestre);
    }

    @Override
    public List<SemestreDto> getAllSemestre() {
        List<Semestre> semestres = semestreRepository.findAll();
        return semestres.stream().map((semestre)-> semestreMapper.mapToSemestreDto(semestre))
                .collect(Collectors.toList());
    }

    @Override
    public Semestre getSemestreById(Integer semestreId) {
        return semestreRepository.findById(semestreId)
                .orElseThrow(() -> new RuntimeException("Semestre not found with id: " + semestreId));
    }
}
