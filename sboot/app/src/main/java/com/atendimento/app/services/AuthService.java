package com.atendimento.app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.atendimento.app.entities.User;
import com.atendimento.app.repositories.UserRepository;
import com.atendimento.app.security.UserPrincipal;

/**
 * Serviço de autenticação para carregar detalhes do usuário.
 * 
 * <p>Este serviço adapta a entidade {@link User} para o contrato {@link UserDetails}
 * do Spring Security, permitindo autenticação e autorização com base nos
 * dados armazenados no banco.</p>
 */
@Service
public class AuthService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Carrega os detalhes do usuário com base no nome de usuário.
     *
     * @param username Nome de usuário do usuário a ser carregado.
     * @return UserDetails contendo os detalhes do usuário.
     * @throws UsernameNotFoundException Caso o usuário não seja encontrado.
     */
    @Override
    @Cacheable(value = "users", key = "#username", unless = "#result == null")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Tentativa de carregar usuário com username: {}", username);
        User user = findUserByUsername(username);
        logger.info("Usuário encontrado: {}", user.getUsername());
        return new UserPrincipal(user);
    }

    /**
     * Busca um usuário pelo nome de usuário no repositório.
     *
     * @param username Nome de usuário.
     * @return A entidade {@link User} correspondente.
     * @throws UsernameNotFoundException Caso o usuário não seja encontrado.
     */
    private User findUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com username: " + username));
    }
}