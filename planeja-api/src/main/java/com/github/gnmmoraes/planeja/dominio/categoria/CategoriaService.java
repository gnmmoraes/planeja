package com.github.gnmmoraes.planeja.dominio.categoria;

import com.github.gnmmoraes.planeja.common.RegistroNaoEncontradoException;
import com.github.gnmmoraes.planeja.common.exceptions.ValidationException;
import com.github.gnmmoraes.planeja.common.validation.ValidationResult;
import com.github.gnmmoraes.planeja.dominio.categoria.dto.CategoriaDetalhes;
import com.github.gnmmoraes.planeja.dominio.categoria.dto.CategoriaForm;
import com.github.gnmmoraes.planeja.dominio.categoria.mapper.CategoriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaValidator validator;
    private final CategoriaMapper mapper;

    public CategoriaDetalhes criar(CategoriaForm categoriaForm) {
        ValidationResult result = validator.validar(categoriaForm);

        if (result.isValido()) {
            throw new ValidationException(result.getCamposInvalido());
        }
        var entity = mapper.toEntity(categoriaForm);
        categoriaRepository.save(entity);

        return mapper.toDetalhes(entity);
    }

    public Page<CategoriaDetalhes> listar(PageRequest pageRequest) {
        return categoriaRepository
                .findAll(pageRequest)
                .map(mapper::toDetalhes);
    }

    @Transactional
    public void mudarStatus(UUID id) {
        var categoriaEntity = categoriaRepository
                .findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);
        categoriaEntity.setAtivo(!categoriaEntity.getAtivo());
    }

    public List<CategoriaDetalhes> listarAtivos() {
        return categoriaRepository
                .findByAtivoTrue()
                .stream()
                .map(mapper::toDetalhes)
                .toList();
    }

    public List<CategoriaDetalhes> listarTodasCategorias() {
        return categoriaRepository
                .findAll(Sort.by("nome"))
                .stream()
                .map(mapper::toDetalhes)
                .toList();
    }
}
