package com.github.gnmmoraes.planeja.infra;

import com.github.gnmmoraes.planeja.dominio.cartao.CartaoRepository;
import com.github.gnmmoraes.planeja.dominio.cartao.model.BandeiraCartao;
import com.github.gnmmoraes.planeja.dominio.cartao.model.CartaoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Sandbox implements CommandLineRunner {

    private final CartaoRepository cartaoRepository;

    public void salvarCartao(){
        CartaoEntity cartao = new CartaoEntity();
        cartao.setNome("ITAU Personalité");
        cartao.setBandeira(BandeiraCartao.AMERICAN_EXPRESS);

        cartaoRepository.save(cartao);
    }

    @Override
    public void run(String... args) throws Exception {
//        salvarCartao();
    }
}
