package com.github.gnmmoraes.planeja.dominio.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginForm(
        @NotBlank(message = "Informe o login")
        String login,
        @NotBlank(message = "Informe o senha")
        String senha ) {
}
