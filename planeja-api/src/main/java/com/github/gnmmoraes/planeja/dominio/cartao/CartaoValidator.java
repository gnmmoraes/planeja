package com.github.gnmmoraes.planeja.dominio.cartao;

import com.github.gnmmoraes.planeja.common.validation.CampoInvalido;
import com.github.gnmmoraes.planeja.common.validation.ValidationResult;
import com.github.gnmmoraes.planeja.dominio.cartao.dto.CartaoForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartaoValidator {

    private final CartaoRepository cartaoRepository;

    public ValidationResult validar(CartaoForm form, UUID id){
        var result = ValidationResult.novo();

        if(!cartaoRepository.findByNomeAndNotID(form.nome(), id).isEmpty()){
            result.add(new CampoInvalido("nome", "já cadastrado"));
        }
        return result;
    }
}
