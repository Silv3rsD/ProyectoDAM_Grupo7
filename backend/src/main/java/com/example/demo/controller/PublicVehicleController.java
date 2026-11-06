package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.VehicleResponse;
import com.example.demo.service.VehicleService;

@RestController
@RequestMapping("/api/public/vehicles")
public class PublicVehicleController {

    private final VehicleService service;

    public PublicVehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping("/plate/{plate}")
    public VehicleResponse find(@PathVariable String plate) {
        return service.findPublicByPlate(plate);
    }
}