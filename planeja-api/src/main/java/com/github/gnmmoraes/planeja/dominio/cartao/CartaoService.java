package com.github.gnmmoraes.planeja.dominio.cartao;

import com.github.gnmmoraes.planeja.common.exceptions.RegistroNaoEncontradoException;
import com.github.gnmmoraes.planeja.common.exceptions.ValidationException;
import com.github.gnmmoraes.planeja.dominio.cartao.dto.CartaoDetalhes;
import com.github.gnmmoraes.planeja.dominio.cartao.dto.CartaoForm;
import com.github.gnmmoraes.planeja.dominio.cartao.mapper.CartaoMapper;
import com.github.gnmmoraes.planeja.dominio.cartao.model.CartaoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository cartaoRepository;
    private final CartaoValidator cartaoValidator;
    private final CartaoMapper mapper;

    public CartaoDetalhes criar(CartaoForm cartaoForm) {
        var validar = cartaoValidator.validar(cartaoForm, null);

        if(validar.isValido()){
            throw new ValidationException(validar.getCamposInvalido());
        }

        CartaoEntity cartao = mapper.toEntity(cartaoForm);
        cartaoRepository.save(cartao);
        return mapper.toDetalhes(cartao);
    }

    public CartaoDetalhes obterDetalhesId(UUID id) {
        return mapper
                .toDetalhes(cartaoRepository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new));
    }

    @Transactional
    public void atualizar(UUID id, CartaoForm dadosAtualizacao) {
        var entity = cartaoRepository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);

        var validar = cartaoValidator.validar(dadosAtualizacao,id);

        if(validar.isValido()){
            throw new ValidationException(validar.getCamposInvalido());
        }
        mapper.updateCartao(entity, dadosAtualizacao);
    }

    public Page<CartaoDetalhes> listar(PageRequest pageRequest) {
        return cartaoRepository
                .findAll(pageRequest)
                .map(mapper::toDetalhes);
    }

    @Transactional
    public void mudarStatus(UUID id){
        var cartao = cartaoRepository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);
        cartao.setAtivo(!cartao.getAtivo());
    }

    public List<CartaoDetalhes> listarAtivas() {
        return cartaoRepository
                .findByAtivoTrue()
                .stream()
                .map(mapper::toDetalhes)
                .toList();
    }
}
