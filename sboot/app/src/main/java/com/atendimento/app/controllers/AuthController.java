package com.atendimento.app.controllers;

import com.atendimento.app.entities.Role;
import com.atendimento.app.entities.User;
import com.atendimento.app.models.LoginRequest;
import com.atendimento.app.models.RegisterRequest;
import com.atendimento.app.repositories.UserRepository;
import com.atendimento.app.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

/**
 * Controlador para autenticação, registro e geração de tokens JWT.
 */
@Tag(name = "Autenticação", description = "Endpoints para autenticação e registro de novos usuários")
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.cookie.name}")
    private String cookieName;

    /**
     * Endpoint para autenticação e geração de JWT.
     *
     * @param request Objeto contendo as credenciais de login.
     * @return Token JWT se a autenticação for bem-sucedida.
     */
    @Operation(summary = "Login do usuário", description = "Autentica o usuário com nome de usuário e senha e retorna um token JWT.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        logger.info("Tentativa de login para o usuário: {}", request.getUsername());
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            String token = jwtTokenProvider.generateToken(auth);
            long expiresIn = jwtTokenProvider.getExpirationMs() / 1000;

            logger.info("Login bem-sucedido para o usuário: {}", request.getUsername());

            // Cria cookie seguro com boas práticas
            ResponseCookie cookie = ResponseCookie.from(cookieName, token)
                .httpOnly(true) // evita acesso via JS
                .secure(true) // HTTPS obrigatório
                .path("/") // escopo do cookie
                .maxAge(Duration.ofSeconds(expiresIn)) // compatível com tempo do token
                .sameSite("Strict") // protege contra CSRF
                .build();

            // Retorna o cookie no header da resposta
            return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(Map.of(
                "message", "Login realizado com sucesso",
                "expiresIn", expiresIn
            ));
        } catch (BadCredentialsException e) {
            logger.warn("Falha na autenticação para o usuário: {}", request.getUsername());
            return ResponseEntity.status(401).body(Map.of(
                "error", "Credenciais inválidas",
                "timestamp", LocalDateTime.now()
            ));
        } catch (org.springframework.security.authentication.LockedException e) {
            logger.warn("Usuário bloqueado tentou logar: {}", request.getUsername());
            return ResponseEntity.status(401).body(Map.of(
                "error", "Usuário bloqueado",
                "message", "Esta conta está bloqueada. Contate o suporte.",
                "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            logger.error("Erro inesperado durante o login do usuário: {}", request.getUsername(), e);
            return ResponseEntity.status(500).body(Map.of(
                "error", "Erro interno no servidor",
                "timestamp", LocalDateTime.now()
            ));
        }
    }

    /**
     * Endpoint para registro de novos usuários.
     *
     * @param request Objeto contendo os dados do novo usuário.
     * @return Mensagem de sucesso ou erro.
     */
    @Operation(summary = "Registro de novo usuário", description = "Registra um novo usuário na aplicação com nome de usuário, senha e papel.")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        logger.info("Tentativa de registro para o usuário: {}", request.getUsername());

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            logger.warn("Tentativa de registro falhou. Usuário já existe: {}", request.getUsername());
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Usuário já existe",
                "timestamp", LocalDateTime.now()
            ));
        }

        if (!isValidRole(request.getRole())) {
            logger.warn("Tentativa de registro falhou. Papel inválido: {}", request.getRole());
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Papel inválido",
                "timestamp", LocalDateTime.now()
            ));
        }

        User newUser = createNewUser(request);
        userRepository.save(newUser);

        logger.info("Usuário registrado com sucesso: {}", newUser.getUsername());
        return ResponseEntity.ok(Map.of(
            "message", "Usuário registrado com sucesso!",
            "username", newUser.getUsername(),
            "timestamp", LocalDateTime.now()
        ));
    }

    /**
     * Valida se o papel fornecido é válido.
     *
     * @param role Papel do usuário.
     * @return true se o papel for válido, false caso contrário.
     */
    private boolean isValidRole(Role role) {
    return Arrays.asList(Role.values()).contains(role);
    }

    /**
     * Cria um novo usuário a partir do request de registro.
     *
     * @param request Objeto contendo os dados do novo usuário.
     * @return Novo objeto User.
     */
    private User createNewUser(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setLocked(false);
        return user;
    }
}