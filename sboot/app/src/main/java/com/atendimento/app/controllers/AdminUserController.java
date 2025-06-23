package com.atendimento.app.controllers;

import com.atendimento.app.entities.User;
import com.atendimento.app.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controlador para gerenciar usuários (admin).
 */
@Tag(name = "Admin", description = "Endpoints para gerenciar usuários (somente administradores)")
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Lista todos os usuários.
     *
     * @return Lista de usuários.
     */
    @Operation(summary = "Listar usuários", description = "Lista todos os usuários cadastrados (somente administradores).")
    @PreAuthorize("hasRole('ADMIN')") // Somente administradores podem acessar este método
    @GetMapping
    public ResponseEntity<List<User>> listarUsuarios() {
        logger.info("Listando todos os usuários (somente administradores).");
        List<User> usuarios = userRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Lista todos os usuários bloqueados.
     *
     * @return Lista de usuários bloqueados.
     */
    @Operation(summary = "Listar usuários bloqueados", description = "Lista todos os usuários que estão bloqueados (somente administradores).")
    @PreAuthorize("hasRole('ADMIN')") // Somente administradores podem acessar este método
    @GetMapping("/blocked")
    public ResponseEntity<List<User>> listarUsuariosBloqueados() {
        logger.info("Listando todos os usuários bloqueados (somente administradores).");
        List<User> usuariosBloqueados = userRepository.findAllByIsLockedTrue();
        if (usuariosBloqueados.isEmpty()) {
            logger.info("Nenhum usuário bloqueado encontrado.");
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se não houver usuários bloqueados
        }
        return ResponseEntity.ok(usuariosBloqueados);
    }

    /**
     * Bloqueia um usuário pelo ID.
     *
     * @param userId ID do usuário a ser bloqueado.
     * @return Mensagem de sucesso ou erro.
     */
    @Operation(summary = "Bloquear usuário", description = "Bloqueia um usuário pelo ID (somente administradores).")
    @PreAuthorize("hasRole('ADMIN')") // Somente administradores podem acessar este método
    @PatchMapping("/{userId}/block")
    public ResponseEntity<?> bloquearUsuario(@PathVariable UUID userId) {
        logger.info("Solicitação para bloquear o usuário com ID: {}", userId);

        Optional<User> usuarioOptional = userRepository.findById(userId);
        if (usuarioOptional.isEmpty()) {
            logger.warn("Usuário com ID {} não encontrado.", userId);
            return ResponseEntity.notFound().build();
        }

        User usuario = usuarioOptional.get();
        if (usuario.isLocked()) {
            logger.warn("Usuário com ID {} já está bloqueado.", userId);
            return ResponseEntity.badRequest().body("Usuário já está bloqueado.");
        }

        usuario.setLocked(true); // Bloqueia o usuário
        userRepository.save(usuario);
        logger.info("Usuário com ID {} foi bloqueado com sucesso.", userId);
        return ResponseEntity.ok("Usuário bloqueado com sucesso.");
    }
}