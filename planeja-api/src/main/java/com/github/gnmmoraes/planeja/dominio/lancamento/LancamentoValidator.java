    package com.github.gnmmoraes.planeja.dominio.lancamento;

    import com.github.gnmmoraes.planeja.common.validation.CampoInvalido;
    import com.github.gnmmoraes.planeja.common.validation.ValidationResult;
    import com.github.gnmmoraes.planeja.dominio.cartao.model.CartaoEntity;
    import com.github.gnmmoraes.planeja.dominio.categoria.model.CategoriaEntity;
    import com.github.gnmmoraes.planeja.dominio.lancamento.dto.LancamentoForm;
    import com.github.gnmmoraes.planeja.dominio.lancamento.model.TipoLancamento;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Component;

    @Component
    @RequiredArgsConstructor
    public class LancamentoValidator {

        public ValidationResult validar(LancamentoForm form, CategoriaEntity categoria, CartaoEntity cartao) {
            var result = ValidationResult.novo();

            if(categoria == null){
                result.add(new CampoInvalido("categoriaId", "Categoria não encontrada."));
            } else if (categoria.getAtivo().equals(Boolean.FALSE)) {
                result.add(new CampoInvalido("categoriaId", "Categoria inativa."));
            }

            if(form.tipo() == TipoLancamento.RECEITA && cartao != null){
                result.add(new CampoInvalido(
                        "cartaoId", "Lancamento do tipo RECEITA não aceita cartão."));
            }

            if(form.tipo() == TipoLancamento.DESPESA && form.cartaoId() != null){
               if(cartao == null){
                   result.add(new CampoInvalido("cartaoId", "Cartão não encontrado."));
               } else if (cartao.getAtivo().equals(Boolean.FALSE)) {
                   result.add(new CampoInvalido("cartaoId", "Cartão inativo."));
               }
            }
            return result;
        }

    }
