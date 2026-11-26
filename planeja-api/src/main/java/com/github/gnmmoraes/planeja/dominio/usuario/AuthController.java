package com.github.gnmmoraes.planeja.dominio.usuario;

import com.github.gnmmoraes.planeja.dominio.usuario.dto.AuthResponse;
import com.github.gnmmoraes.planeja.dominio.usuario.dto.LoginForm;
import com.github.gnmmoraes.planeja.dominio.usuario.dto.UsuarioCadastroForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/signup")
    public AuthResponse cadastrar(@RequestBody @Valid UsuarioCadastroForm form){
        return service.cadastrar(form);
    }

    @PostMapping("/signin")
    public AuthResponse autenticar(@RequestBody @Valid LoginForm form){
        return service.autenticar(form);
    }
}
