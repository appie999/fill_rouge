package com.fillrougeratt.fillrougebackend.service;


import com.fillrougeratt.fillrougebackend.dto.PatientDto;
import com.fillrougeratt.fillrougebackend.mapper.PatientMapper;
import com.fillrougeratt.fillrougebackend.model.Patient;
import com.fillrougeratt.fillrougebackend.repo.PatientRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepo repo;
    private final PatientMapper patientMapper;

    public PatientService(PatientRepo repo, PatientMapper patientMapper) {
        this.repo = repo;
        this.patientMapper = patientMapper;
    }


    public PatientDto savePatient(PatientDto dto){
        return patientMapper.toDto(repo.save(patientMapper.toEntity(dto)));
    }

    public List<PatientDto> getAllPatient(){
        return repo.findAll().stream().map(patient -> patientMapper.toDto(patient)).toList();
    }

    public PatientDto getPatientById(Long id){
        Patient patient = repo.findById(id).orElseThrow(()->new RuntimeException("patient not found"));
        return patientMapper.toDto(patient);
    }

    public PatientDto editPatient(Long id , PatientDto dto){
        Patient patient = repo.findById(id).get();
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setUserName(dto.getUserName());
        patient.setEmail(dto.getEmail());

        return patientMapper.toDto(patient);
    }

    public void deletePatient(Long id){
        repo.deleteById(id);
    }
}
