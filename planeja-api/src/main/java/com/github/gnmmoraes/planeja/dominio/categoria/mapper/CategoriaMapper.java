package com.github.gnmmoraes.planeja.dominio.categoria.mapper;

import com.github.gnmmoraes.planeja.dominio.categoria.dto.CategoriaDetalhes;
import com.github.gnmmoraes.planeja.dominio.categoria.dto.CategoriaForm;
import com.github.gnmmoraes.planeja.dominio.categoria.model.CategoriaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaEntity toEntity(CategoriaForm form);
    CategoriaDetalhes toDetalhes(CategoriaEntity entity);

}
