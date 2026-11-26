package com.github.gnmmoraes.planeja.dominio.usuario.dto;

public record AuthResponse(
        String token,
        String tipo,
        long expiraEm,
        String nome ) {
}
