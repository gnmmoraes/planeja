package com.github.gnmmoraes.planeja.dominio.categoria;

import com.github.gnmmoraes.planeja.common.validation.CampoInvalido;
import com.github.gnmmoraes.planeja.common.validation.ValidationResult;
import com.github.gnmmoraes.planeja.dominio.categoria.dto.CategoriaForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoriaValidator {

    private final CategoriaRepository categoriaRepository;

    public ValidationResult validar(CategoriaForm categoriaForm) {
        var result = ValidationResult.novo();
        var categoriaCadastrada = categoriaRepository.existsByNome(categoriaForm.nome());
        if (categoriaCadastrada) {
            result.add(new CampoInvalido("nome", "Nome de categoria já cadastrada."));
        }
        return result;
    }
}
