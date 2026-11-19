package com.github.gnmmoraes.planeja.dominio.lancamento;

import com.github.gnmmoraes.planeja.dominio.cartao.CartaoService;
import com.github.gnmmoraes.planeja.dominio.cartao.dto.CartaoDetalhes;
import com.github.gnmmoraes.planeja.dominio.categoria.CategoriaService;
import com.github.gnmmoraes.planeja.dominio.categoria.dto.CategoriaDetalhes;
import com.github.gnmmoraes.planeja.dominio.lancamento.dto.LancamentoDetalhes;
import com.github.gnmmoraes.planeja.dominio.lancamento.dto.LancamentoForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lancamentos")
@RequiredArgsConstructor
public class CadastroLancamentoController {

    private final LancamentoService service;
    private final CategoriaService categoriaService;
    private final CartaoService  cartaoService;

//    @PostMapping
//    public ResponseEntity<LancamentoDetalhes> criar(@RequestBody @Valid LancamentoForm form) {
//        LancamentoDetalhes detalhes = service.criar(form);
//        return ResponseEntity.status(HttpStatus.CREATED).body(detalhes);
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LancamentoDetalhes criar(@RequestBody @Valid LancamentoForm form) {
        return service.criar(form);
    }

    @GetMapping("categorias-disponiveis")
    public List<CategoriaDetalhes> listarCategoriasDisponiveis() {
        return categoriaService.listarAtivos();
    }

    @GetMapping("cartoes-disponiveis")
    public List<CartaoDetalhes> listarCartoesDisponiveis() {
        return cartaoService.listarAtivas();
    }






}
