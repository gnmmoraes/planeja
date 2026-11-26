package com.github.gnmmoraes.planeja.dominio.usuario;

import com.github.gnmmoraes.planeja.dominio.usuario.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {

    Optional<UsuarioEntity> findByLogin(String login);

    boolean existsByLogin(String login);


}
