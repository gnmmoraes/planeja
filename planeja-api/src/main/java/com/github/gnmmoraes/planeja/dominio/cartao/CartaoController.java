package com.github.gnmmoraes.planeja.dominio.cartao;

import com.github.gnmmoraes.planeja.dominio.cartao.dto.CartaoDetalhes;
import com.github.gnmmoraes.planeja.dominio.cartao.dto.CartaoForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartaoController {

    private final CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<CartaoDetalhes> criar(@RequestBody @Valid CartaoForm novo) {
        CartaoDetalhes detalhes = cartaoService.criar(novo);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalhes);
    }

    @GetMapping("{id}")
    public ResponseEntity<CartaoDetalhes> obterDetalhes(@PathVariable UUID id) {
        var result = cartaoService.obterDetalhesId(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@RequestBody @Valid CartaoForm novo, @PathVariable UUID id) {
        cartaoService.atualizar(id, novo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public Page<CartaoDetalhes> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size){

        var pageRequest = PageRequest.of(page, size);
        return cartaoService.listar(pageRequest);
    }

    @PatchMapping("{id}/status")
    public ResponseEntity<Void> atualizarCartao(@PathVariable UUID id) {
        cartaoService.mudarStatus(id);
        return ResponseEntity.noContent().build();
    }


}
