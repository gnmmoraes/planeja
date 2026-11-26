package com.github.gnmmoraes.planeja.dominio.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCadastroForm(

        @NotBlank(message = "Informe o nome do usuário")
        String nome,
        @NotBlank(message = "Informe o login do usuário")
        String login,
        @NotBlank(message = "Informe o senha do usuário")
        @Size(min = 6, message = "A senha deve conter no minimo 6 caracteres")
        String senha ) {
}
