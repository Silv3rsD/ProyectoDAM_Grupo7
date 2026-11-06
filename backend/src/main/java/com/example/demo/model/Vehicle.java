package com.example.demo.model;

/**
 * ENTIDAD: representa una fila de la tabla "vehicles" de MySQL.
 * Cada atributo se corresponde con una columna (@Column indica el nombre real en la BD).
 * No incluye created_at ni created_by a propósito: así nunca pueden salir en la API.
 * Es la única clase que "conoce" la estructura de la tabla.
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @Column(name = "vin_number", length = 17)
    private String vin;

    @Column(name = "license_plate", nullable = false, unique = true, length = 15)
    private String licensePlate;

    private String brand;

    private String model;

    @Column(name = "manufacturing_year")
    private Integer manufacturingYear;

    @Column(name = "current_mileage")
    private Integer currentMileage;

    @Column(nullable = false)
    private boolean validated;

    protected Vehicle() {}

    public String getVin() { return vin; }
    public String getLicensePlate() { return licensePlate; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public Integer getManufacturingYear() { return manufacturingYear; }
    public Integer getCurrentMileage() { return currentMileage; }
    public boolean isValidated() { return validated; }
}
