package com.github.gnmmoraes.planeja.dominio.cartao.dto;

import com.github.gnmmoraes.planeja.dominio.cartao.model.BandeiraCartao;

import java.time.LocalDateTime;

public record CartaoDetalhes(
        String id,
        String nome,
        BandeiraCartao bandeira,
        LocalDateTime dataCadastro,
        Boolean ativo) {
}
