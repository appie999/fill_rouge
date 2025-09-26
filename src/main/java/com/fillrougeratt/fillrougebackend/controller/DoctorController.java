package com.fillrougeratt.fillrougebackend.controller;


import com.fillrougeratt.fillrougebackend.dto.DoctorDto;
import com.fillrougeratt.fillrougebackend.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @PostMapping("")
    public DoctorDto saveDoctor(@RequestBody DoctorDto dto){
        return service.saveDoctor(dto);
    }

    @GetMapping("/{id}")
    public DoctorDto getDoctorById(@PathVariable Long id){
        return service.getDoctorById(id);
    }

    @GetMapping("/")
    public List<DoctorDto> getAllDoctors(){
        return service.getAllDoctors();
    }

    @PutMapping("/{id}")
    public DoctorDto editDoctor(@PathVariable Long id , @RequestBody DoctorDto dto){
        return service.editDoctor(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteDector(@PathVariable Long id){
        service.deleteDoctor(id);
    }
}
