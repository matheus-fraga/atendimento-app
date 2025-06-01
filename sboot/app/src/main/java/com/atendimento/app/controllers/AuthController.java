package com.atendimento.app.controllers;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atendimento.app.entities.Role;
import com.atendimento.app.entities.User;
import com.atendimento.app.models.LoginRequest;
import com.atendimento.app.models.RegisterRequest;
import com.atendimento.app.models.TokenResponse;
import com.atendimento.app.repositories.UserRepository;
import com.atendimento.app.security.JwtTokenProvider;

import jakarta.validation.Valid;
import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controlador para autenticação, registro e geração de tokens JWT.
 */
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

    /**
     * Endpoint para autenticação e geração de JWT.
     *
     * @param request Objeto contendo as credenciais de login.
     * @return ResponseEntity contendo o token JWT ou uma mensagem de erro.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        logger.info("Tentativa de login para o usuário: {}", request.getUsername());
        try {
            // Autenticação do usuário
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            // Gera o token JWT
            String token = jwtTokenProvider.generateToken(auth);

            // Tempo de expiração do token (em segundos)
            long expiresIn = jwtTokenProvider.getExpirationMs() / 1000;

            // Retorna o token em caso de sucesso
            logger.info("Login bem-sucedido para o usuário: {}", request.getUsername());
            return ResponseEntity.ok(new TokenResponse(token, expiresIn));
        } catch (BadCredentialsException e) {
            // Retorna erro 401 sem especificar se o problema foi username ou password
            logger.warn("Falha na autenticação para o usuário: {}", request.getUsername());
            return ResponseEntity.status(401).body(Map.of(
                "error", "Credenciais inválidas",
                "timestamp", LocalDateTime.now()
            ));
        } catch (Exception e) {
            // Trata erros inesperados
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
     * @return ResponseEntity com mensagem de sucesso ou erro.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        logger.info("Tentativa de registro para o usuário: {}", request.getUsername());

        // Verifica se o usuário já existe
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            logger.warn("Tentativa de registro falhou. Usuário já existe: {}", request.getUsername());
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Usuário já existe",
                "timestamp", LocalDateTime.now()
            ));
        }

        // Verifica se o papel é válido
        if (!isValidRole(request.getRole())) {
            logger.warn("Tentativa de registro falhou. Papel inválido: {}", request.getRole());
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Papel inválido",
                "timestamp", LocalDateTime.now()
            ));
        }

        // Cria o novo usuário
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
        return role == Role.USER || role == Role.ADMIN;
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
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash da senha
        user.setRole(request.getRole());
        user.setLocked(false); // Usuário não bloqueado por padrão
        return user;
    }

    /**
     * Manipulador para exceções de validação.
     *
     * @param ex Exceção de validação.
     * @return ResponseEntity contendo os detalhes do erro.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleValidationExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
            .collect(Collectors.toMap(
                violation -> violation.getPropertyPath().toString(),
                violation -> violation.getMessage()
            ));
        return ResponseEntity.badRequest().body(Map.of(
            "error", "Erro de validação",
            "details", errors,
            "timestamp", LocalDateTime.now()
        ));
    }
}