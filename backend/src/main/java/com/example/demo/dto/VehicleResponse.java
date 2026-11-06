package com.example.demo.dto;

public record VehicleResponse(
        String vin,
        String licensePlate,
        String brand,
        String model,
        Integer manufacturingYear,
        Integer currentMileage) {
}