package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Usuario y contraseña para autenticarse
 */
public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password) {
}