package com.example.mySpringProject.dto;

public class AddEtudiantAClasseDto {
    private Integer etudiantId;
    private Integer classeId;
    private Integer groupeId;

    // Getters et setters
    public int getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Integer etudiantId) {
        this.etudiantId = etudiantId;
    }

    public Integer getClasseId() {
        return classeId;
    }

    public void setClasseId(Integer classeId) {
        this.classeId = classeId;
    }

    public Integer getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(Integer groupeId) {
        this.groupeId = groupeId;
    }
}
