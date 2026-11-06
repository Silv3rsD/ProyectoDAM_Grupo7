package com.example.demo.service;

import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dto.VehicleResponse;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.VehicleRepository;

@Service
public class VehicleService {

    private static final Pattern PLATE = Pattern.compile("^[A-Z0-9-]{4,15}$");

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public VehicleResponse findPublicByPlate(String raw) {
        String plate = raw == null ? "" : raw.trim().toUpperCase();

        if (!PLATE.matcher(plate).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Matrícula inválida");
        }

        Vehicle v = repository.findByLicensePlateAndValidatedTrue(plate)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Vehículo no encontrado"));

        return new VehicleResponse(
                v.getVin(),
                v.getLicensePlate(),
                v.getBrand(),
                v.getModel(),
                v.getManufacturingYear(),
                v.getCurrentMileage());
    }
}