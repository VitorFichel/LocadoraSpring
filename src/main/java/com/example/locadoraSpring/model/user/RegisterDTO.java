package com.example.locadoraSpring.model.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
