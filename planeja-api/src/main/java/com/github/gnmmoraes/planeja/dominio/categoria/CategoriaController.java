package com.github.gnmmoraes.planeja.dominio.categoria;

import com.github.gnmmoraes.planeja.dominio.categoria.dto.CategoriaDetalhes;
import com.github.gnmmoraes.planeja.dominio.categoria.dto.CategoriaForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @PostMapping
    public ResponseEntity<CategoriaDetalhes> criar(
            @RequestBody @Valid CategoriaForm categoriaForm){
        CategoriaDetalhes detalhes = service.criar(categoriaForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(detalhes);
    }

    @GetMapping
    public  Page<CategoriaDetalhes> listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "0") int size){

        var pageRequest = PageRequest.of(page,size);
        return service.listar(pageRequest);
    }

    @PatchMapping("{id}/status")
    public ResponseEntity<Void> mudarStatus(@PathVariable UUID id) {
        service.mudarStatus(id);
        return ResponseEntity.noContent().build();
    }
}
