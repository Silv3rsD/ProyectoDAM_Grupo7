package com.example.demo.dto;

/*
 * Devuelve un mensaje.
 * Es usada por los endpoints que no hacen return de nada y solo devuelven un mensaje
 */
public record MessageResponse(String message) {
}