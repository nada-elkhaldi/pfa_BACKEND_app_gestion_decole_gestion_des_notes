package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.dto.FeuDto;
import com.example.mySpringProject.exception.ResourceNotFoundException;
import com.example.mySpringProject.mapper.FeuMapper;
import com.example.mySpringProject.model.Feu;
import com.example.mySpringProject.repository.FeuRepository;
import com.example.mySpringProject.service.FeuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FeuServiceImpl implements FeuService {


    private FeuRepository feuRepository;
    private FeuMapper feuMapper;
    @Override
    public FeuDto addFeu(FeuDto feuDto) {
        Feu feu = FeuMapper.mapToFeu(feuDto);
        Feu savedFeu = feuRepository.save(feu);
        return FeuMapper.mapToFeuDto(savedFeu);
    }

    @Override
    public List<FeuDto> getAllFeux() {
        List<Feu> feux = feuRepository.findAll();
        return feux.stream().map((feu)-> FeuMapper.mapToFeuDto(feu))
                .collect(Collectors.toList());
    }

    @Override
    public Feu getFeuById(Integer id) {
        return feuRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Feu with id " + id + " not found"));

    }

    @Override
    public Feu updateFeu(Integer id, Feu request) {
        Feu feu = feuRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Region with id " + id + " not found")
        );
        feu.setNumero(request.getNumero());
        feu.setNomLocalisation(request.getNomLocalisation());
        feu.setPosition(request.getPosition());
        feu.setCaracteristiques(request.getCaracteristiques());
        feu.setDescription(request.getDescription());
        feu.setElevation(request.getElevation());
        feu.setPortee(request.getPortee());
        feu.setInfos(request.getInfos());
        Feu savedFeu = feuRepository.save(feu);
        return savedFeu;
    }

    @Override
    public void deleteFeu(Integer id) {
        Feu feu = feuRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Feu with id " + id + " not found")
        );
        feuRepository.deleteById(id);
    }
}
