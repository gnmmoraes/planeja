package com.github.gnmmoraes.planeja.dominio.dashboard;

import com.github.gnmmoraes.planeja.dominio.dashboard.dto.Dashboard;
import com.github.gnmmoraes.planeja.dominio.dashboard.dto.DespesasCategoriaResumo;
import com.github.gnmmoraes.planeja.dominio.dashboard.dto.ResumoFinanceiro;
import com.github.gnmmoraes.planeja.dominio.lancamento.LancamentoRepository;
import com.github.gnmmoraes.planeja.dominio.lancamento.model.TipoLancamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final LancamentoRepository lancamentoRepository;

    public Dashboard obterDashboardMesAtual() {

        YearMonth mesAtual = YearMonth.now();
        LocalDate primeiroDiaMes = mesAtual.atDay(1);
        LocalDate ultimoDiaMes = mesAtual.atEndOfMonth();

        BigDecimal receitas = lancamentoRepository
                .somaPorTipoNoPeriodo(TipoLancamento.RECEITA, primeiroDiaMes, ultimoDiaMes);

        BigDecimal despesas = lancamentoRepository
                .somaPorTipoNoPeriodo(TipoLancamento.DESPESA, primeiroDiaMes, ultimoDiaMes);

        BigDecimal saldo = receitas.subtract(despesas);

        var resumoFinanceiro = new ResumoFinanceiro(receitas, despesas, saldo);

        List<DespesasCategoriaResumo> despesasCategoriaResumos = lancamentoRepository
                .somaPorCategoriaNoPeriodo(TipoLancamento.DESPESA, primeiroDiaMes, ultimoDiaMes)
                .stream()
                .map(resultado ->
                        new DespesasCategoriaResumo(
                                (String) resultado[0],
                                (BigDecimal) resultado[1])
                ).toList();

        return new Dashboard(mesAtual.toString(), resumoFinanceiro, despesasCategoriaResumos);


    }
}
