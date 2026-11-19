package com.github.gnmmoraes.planeja.dominio.cartao.dto;

import com.github.gnmmoraes.planeja.dominio.cartao.model.BandeiraCartao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CartaoForm(
        @NotBlank(message = "Campo obrigatorio.")
        String nome,
        @NotNull(message = "Campo obrigatorio.")
        BandeiraCartao bandeira) {
}
