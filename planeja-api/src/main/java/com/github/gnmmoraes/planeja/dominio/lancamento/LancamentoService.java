package com.github.gnmmoraes.planeja.dominio.lancamento;

import com.github.gnmmoraes.planeja.common.RegistroNaoEncontradoException;
import com.github.gnmmoraes.planeja.common.exceptions.ValidationException;
import com.github.gnmmoraes.planeja.dominio.cartao.CartaoRepository;
import com.github.gnmmoraes.planeja.dominio.cartao.model.CartaoEntity;
import com.github.gnmmoraes.planeja.dominio.categoria.CategoriaRepository;
import com.github.gnmmoraes.planeja.dominio.categoria.model.CategoriaEntity;
import com.github.gnmmoraes.planeja.dominio.lancamento.dto.LancamentoDetalhes;
import com.github.gnmmoraes.planeja.dominio.lancamento.dto.LancamentoForm;
import com.github.gnmmoraes.planeja.dominio.lancamento.mapper.LancamentoMapper;
import com.github.gnmmoraes.planeja.dominio.lancamento.model.LancamentoEntity;
import com.github.gnmmoraes.planeja.dominio.lancamento.model.TipoLancamento;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.UUID;

import static com.github.gnmmoraes.planeja.dominio.lancamento.LancamentoSpecs.*;

@Service
@RequiredArgsConstructor
public class LancamentoService {

    private final LancamentoRepository lancamentoRepository;
    private final CategoriaRepository categoriaRepository;
    private final CartaoRepository cartaoRepository;
    private final LancamentoValidator validator;
    private final LancamentoMapper mapper;


    public LancamentoDetalhes criar(LancamentoForm form) {
        CategoriaEntity categoria = categoriaRepository
                .findById(form.categoriaId())
                .orElse(null);

        CartaoEntity cartao = null;
        if (form.cartaoId() != null) {
            cartao = cartaoRepository
                    .findById(form.cartaoId())
                    .orElse(null);
        }

        var result = validator.validar(form, categoria, cartao);
        if(result.isValido()){
            throw new ValidationException(result.getCamposInvalido());
        }

        LancamentoEntity entity = mapper.toEntity(form, categoria, cartao);
        lancamentoRepository.save(entity);

        return mapper.toDetalhes(entity);
    }

    public Page<LancamentoDetalhes> listar(
            PageRequest pageRequest, YearMonth mes, TipoLancamento tipo, UUID categoriaId){

        Specification<LancamentoEntity> specs = Specification.unrestricted();

        if(tipo != null){
            specs = specs.and(tipoEqual(tipo));
        }

        if(categoriaId != null){
            specs = specs.and(categoriaEqual(categoriaId));
        }

        if (mes != null) {
            specs = specs.and(mesEqual(mes));
        }

        return lancamentoRepository
                .findAll(specs, pageRequest)
                .map(mapper::toDetalhes);
    }

    public void deletar(UUID id){
        var lancamento = lancamentoRepository.findById(id)
                .orElseThrow(RegistroNaoEncontradoException::new);
        lancamentoRepository.delete(lancamento);
    }

}
