package com.atendimento.app.security;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.atendimento.app.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.Cookie;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Ignorar validação para rotas públicas (ex.: /auth/**)
            String path = request.getRequestURI();
            if (path.startsWith("/auth/")) {
                logger.info("Ignorando validação de JWT para a rota pública: {}", path);
                filterChain.doFilter(request, response);
                return;
            }

            // Resolve o token do cabeçalho "Authorization"
            String token = resolveToken(request);

            // Valida o token
            if (token == null || !jwtTokenProvider.validateToken(token)) {
                logger.warn("Token inválido ou expirado. Método: {}, IP: {}, Endpoint: {}",
                        request.getMethod(), request.getRemoteAddr(), request.getRequestURI());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado");
                return;
            }

            // Obtém o nome de usuário do token
            String username = jwtTokenProvider.getUsernameFromToken(token);
            logger.info("Autenticando usuário: {}, Método: {}, IP: {}, Endpoint: {}",
                    username, request.getMethod(), request.getRemoteAddr(), request.getRequestURI());

            // Carrega os detalhes do usuário e autentica
            UserDetails userDetails = authService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Continua o fluxo do filtro
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            // Trata erros inesperados no filtro
            logger.error("Erro inesperado no JwtAuthFilter. Método: {}, IP: {}, Endpoint: {}, Erro: {}",
                    request.getMethod(), request.getRemoteAddr(), request.getRequestURI(), e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno no servidor");
        }
    }

    /**
     * Resolve o token JWT do cabeçalho "Authorization".
     *
     * @param request A requisição HTTP.
     * @return O token JWT, ou null se o token não estiver presente ou estiver mal formatado.
     */
    private String resolveToken(HttpServletRequest request) {
       Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
        }
         return null;
    }
}