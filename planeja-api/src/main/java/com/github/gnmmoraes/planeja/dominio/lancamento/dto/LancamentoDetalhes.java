package com.github.gnmmoraes.planeja.dominio.lancamento.dto;

import com.github.gnmmoraes.planeja.dominio.lancamento.model.TipoLancamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LancamentoDetalhes(
        String id,
        String descricao,
        LocalDate data,
        BigDecimal valor,
        TipoLancamento tipo,
        String categoriaId,
        String categoriaNome,
        String cartaoId,
        String cartaoNome) {
}
