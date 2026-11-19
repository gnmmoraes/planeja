package com.github.gnmmoraes.planeja.dominio.dashboard.dto;

import java.util.List;

public record Dashboard(
        String mes,
        ResumoFinanceiro resumo,
        List<DespesasCategoriaResumo> despesasCategoria) {
}
