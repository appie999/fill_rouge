package com.fillrougeratt.fillrougebackend.controller;


import com.fillrougeratt.fillrougebackend.dto.PatientDto;
import com.fillrougeratt.fillrougebackend.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @PostMapping("/")
    public PatientDto savePatient(@RequestBody PatientDto dto){
        return service.savePatient(dto);
    }

    @GetMapping()
    public List<PatientDto> getAllPatient(){
        return service.getAllPatient();
    }

    @GetMapping("/{id}")
    public PatientDto getPatientById(@PathVariable Long id){
        return service.getPatientById(id);
    }

    @PutMapping("/{id}")
    public PatientDto editPatient(@PathVariable Long id, @RequestBody PatientDto dto){
        return service.editPatient(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id){
        service.deletePatient(id);
    }

}
