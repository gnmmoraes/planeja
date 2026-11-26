package com.github.gnmmoraes.planeja.infra.security;

import com.github.gnmmoraes.planeja.dominio.usuario.UsuarioRepository;
import com.github.gnmmoraes.planeja.dominio.usuario.model.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final UsuarioRepository usuarioRepository;

    private static final String ALGORITIMO = "HmacSHA256";

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-seconds}")
    private long expirationSeconds;

    public String gerarToken(UsuarioEntity usuario){
        long expiration = Instant.now().plusSeconds(expirationSeconds).getEpochSecond();

        String header = base64Url("""
                {"alg": "HS256", "typ": "JWT"}                
                """);

        String payload = base64Url("""
                {"sub": "%s", "nome": "%s", "exp": %d }
                """.formatted(usuario.getId().toString(), usuario.getNome(), expiration));

        String signature = assinar(header + "." + payload);

        return header + "." + payload + "." + signature;
    }

    private String assinar(String conteudo){
        try {
            Mac mac = Mac.getInstance(ALGORITIMO);
            byte[] bytesSecret = secret.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(bytesSecret, ALGORITIMO);
            mac.init(secretKey);

            byte[] publicKey = mac.doFinal(conteudo.getBytes(StandardCharsets.UTF_8));

            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(publicKey);
        }catch (Exception e ){
            throw new IllegalStateException("Erro ao errar JWT.", e);
        }
    }

    private String base64Url(String valor){
        return Base64.getEncoder()
                .withoutPadding()
                .encodeToString(valor.getBytes(StandardCharsets.UTF_8));
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }

    public boolean isTokenValido(String token) {
        try {
            String[] partesDoToken = token.split("\\.");
            String header = partesDoToken[0];
            String payload = partesDoToken[1];

            String assinaturaEsperada = assinar(header + "." + payload);
            if (!assinaturaEsperada.equals(partesDoToken[2])) {
                return false;
            }

            var payloadDecodificado = Base64.getDecoder().decode(payload);
            var payloadString = new String(payloadDecodificado, StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(payloadString);
            long expiraEm = json.get("exp").asLong();

            long miliSegundosDataAtual = Instant.now().getEpochSecond();

            return miliSegundosDataAtual < expiraEm ;

        }catch (RuntimeException e ){
            return false;
        }
    }

    public UsuarioEntity getUsuario(String token) {

        String[] partesDoToken = token.split("\\.");

        byte[] payloadDecodificado = Base64.getDecoder().decode(partesDoToken[1]);
        String payload = new String(payloadDecodificado, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(payload);

        String id = json.get("sub").asString();

        var usuario = usuarioRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Erro ao tentar resolver usuario"));
        return usuario;
    }
}
