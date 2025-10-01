package com.fillrougeratt.fillrougebackend.controller;


import com.fillrougeratt.fillrougebackend.dto.AppointmentDto;
import com.fillrougeratt.fillrougebackend.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping
    public AppointmentDto saveAppointment(@RequestBody AppointmentDto dto){
        return service.saveAppointment(dto);
    }

    @GetMapping("/{id}")
    public AppointmentDto getAppointmentById(@PathVariable Long id){
        return service.getAppointmentById(id);
    }

    @GetMapping
    public List<AppointmentDto> getAllAppointment(){
        return service.getAllAppointment();
    }

//    @PutMapping()

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id){
        service.deleteAppointment(id);
    }

}
