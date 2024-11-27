package com.example.mySpringProject.service;

import com.example.mySpringProject.dto.FeuDto;
import com.example.mySpringProject.model.Feu;

import java.util.List;

public interface FeuService {

    FeuDto addFeu(FeuDto feuDto);
    List<FeuDto> getAllFeux();
    Feu getFeuById(Integer id);
    Feu updateFeu(Integer id, Feu feu);
    void deleteFeu(Integer id);

    List<FeuDto> getFeuxByProvince(Integer idProvince);
}
