package com.github.gnmmoraes.planeja.dominio.usuario;

import com.github.gnmmoraes.planeja.common.exceptions.UnauthorizedException;
import com.github.gnmmoraes.planeja.common.exceptions.ValidationException;
import com.github.gnmmoraes.planeja.common.validation.CampoInvalido;
import com.github.gnmmoraes.planeja.dominio.usuario.dto.AuthResponse;
import com.github.gnmmoraes.planeja.dominio.usuario.dto.LoginForm;
import com.github.gnmmoraes.planeja.dominio.usuario.dto.UsuarioCadastroForm;
import com.github.gnmmoraes.planeja.dominio.usuario.model.UsuarioEntity;
import com.github.gnmmoraes.planeja.infra.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AuthResponse cadastrar(UsuarioCadastroForm form){

        if (usuarioRepository.existsByLogin(form.login())) {
            throw new ValidationException(List.of(new CampoInvalido("login", "Login já cadastrado.")));
        }
        var usuario = new UsuarioEntity();
        usuario.setNome(form.nome());
        usuario.setLogin(form.login());
        usuario.setSenha(encoder.encode(form.senha()));
        usuarioRepository.save(usuario);
        return toAuthResponse(usuario);
    }

    public AuthResponse autenticar(LoginForm form){

        final String mensagem = "Login ou senha inválidos.";

        UsuarioEntity usuario = usuarioRepository.findByLogin(form.login())
                .orElseThrow(() -> new UnauthorizedException(mensagem));

        boolean keyPasswords = encoder.matches(form.senha(), usuario.getSenha());

        if (!keyPasswords) {
            throw new UnauthorizedException(mensagem);
        }
        return toAuthResponse(usuario);
    }

    private AuthResponse toAuthResponse(UsuarioEntity usuario){
        var token = jwtService.gerarToken(usuario);
        var expiraEm = jwtService.getExpirationSeconds();

        return new AuthResponse(
                token,
                "Bearer",
                expiraEm,
                usuario.getNome());
    }
}
