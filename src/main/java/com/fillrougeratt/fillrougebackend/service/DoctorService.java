package com.fillrougeratt.fillrougebackend.service;


import com.fillrougeratt.fillrougebackend.dto.DoctorDto;
import com.fillrougeratt.fillrougebackend.mapper.DoctorMapper;
import com.fillrougeratt.fillrougebackend.model.Doctor;
import com.fillrougeratt.fillrougebackend.repo.DoctorRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepo repo;
    private final DoctorMapper mapper;

    public DoctorService(DoctorRepo repo, DoctorMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public DoctorDto saveDoctor(DoctorDto doctorDto){
        return mapper.toDto(repo.save(mapper.toEntity(doctorDto)));
    }

    public List<DoctorDto> getAllDoctors(){
        return repo.findAll().stream().map(doctor -> mapper.toDto(doctor)).toList();
    }

    public DoctorDto getDoctorById(Long id){
        Doctor doctor = repo.findById(id).orElseThrow(()->new RuntimeException("Doctor not found"));
        return mapper.toDto(doctor);
    }

    public DoctorDto editDoctor(Long id , DoctorDto dto){
        Doctor doctor = repo.findById(id).get();
        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setUserName(dto.getUserName());
        doctor.setEmail(dto.getEmail());
        doctor.setPassWord(doctor.getPassWord());

        return mapper.toDto(repo.save(doctor));
    }

    public void deleteDoctor(Long id){
        repo.deleteById(id);
    }
}
