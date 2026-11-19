package com.github.gnmmoraes.planeja.dominio.categoria.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaForm(
        @NotBlank(message = "Campo obrigatorio.")
        String nome) {
}
