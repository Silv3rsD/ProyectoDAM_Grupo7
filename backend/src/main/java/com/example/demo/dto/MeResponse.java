package com.example.demo.dto;

/*
 * Datos del usuario una vez está autenticado
 */
public record MeResponse(String username, String email, String role, String accountStatus) {
}