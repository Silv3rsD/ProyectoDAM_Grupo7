package com.example.demo.repository;

/**
 * REPOSITORIO: la capa que habla con la base de datos.
 * Al extender JpaRepository, Spring genera solo las consultas básicas (guardar, buscar por VIN...).
 * El método findByLicensePlateAndValidatedTrue lo interpreta Spring por su nombre y crea:
 * SELECT * FROM vehicles WHERE license_plate = ? AND validated = TRUE.
 * Así, un vehículo no validado nunca se devuelve (seguridad para no mostrar registros internos).
 */

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    Optional<Vehicle> findByLicensePlateAndValidatedTrue(String licensePlate);
}