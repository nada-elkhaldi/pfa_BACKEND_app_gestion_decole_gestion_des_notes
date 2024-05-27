package com.example.mySpringProject.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class PlanificationRequest {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer semesterId;
    private Integer groupId;
    private Integer classeId;
    private Integer subjectId;
    private Integer teacherId;

    public PlanificationRequest(Integer id, Integer semesterId, Integer groupId, Integer classeId, Integer subjectId, Integer teacherId) {
        this.id = id;
        this.semesterId = semesterId;
        this.groupId = groupId;
        this.classeId = classeId;
        this.subjectId = subjectId;
        this.teacherId = teacherId;
    }
    public PlanificationRequest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getClasseId() {
        return classeId;
    }

    public void setClasseId(Integer classeId) {
        this.classeId = classeId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }
}