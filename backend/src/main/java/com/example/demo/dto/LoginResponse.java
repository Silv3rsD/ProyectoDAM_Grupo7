package com.example.demo.dto;

/**
 * Respuesta de la autenticacion
 */
public record LoginResponse(String token, String role) {
}