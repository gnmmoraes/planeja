package com.github.gnmmoraes.planeja.dominio.dashboard.dto;

import java.math.BigDecimal;

public record DespesasCategoriaResumo(
        String categoria,
        BigDecimal valor) {
}
