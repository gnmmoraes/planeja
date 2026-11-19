package com.github.gnmmoraes.planeja.dominio.cartao.mapper;

import com.github.gnmmoraes.planeja.dominio.cartao.dto.CartaoDetalhes;
import com.github.gnmmoraes.planeja.dominio.cartao.dto.CartaoForm;
import com.github.gnmmoraes.planeja.dominio.cartao.model.CartaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartaoMapper {
    CartaoEntity toEntity(CartaoForm form);
    CartaoDetalhes toDetalhes(CartaoEntity entity);

    void updateCartao(@MappingTarget CartaoEntity entity, CartaoForm dadosAtualizados);

}
