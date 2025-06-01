package com.atendimento.app.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Método chamado automaticamente sempre que uma tentativa de autenticação falha.
     *
     * @param request       A requisição HTTP que falhou.
     * @param response      A resposta HTTP a ser enviada ao cliente.
     * @param authException A exceção lançada durante a tentativa de autenticação.
     * @throws IOException Caso haja um erro ao escrever a resposta.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // Obter informações adicionais
        String userAgent = request.getHeader("User-Agent");
        String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);

        // Logar o erro com detalhes
        logger.warn("Falha na autenticação. Método: {}, IP: {}, Endpoint: {}, User-Agent: {}, Erro: {}",
                request.getMethod(), request.getRemoteAddr(), request.getRequestURI(), userAgent, authException.getMessage());

        // Configurar a resposta HTTP
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Construir a resposta JSON
        String jsonResponse = String.format(
                "{" +
                        "\"error\": \"Acesso não autorizado\"," +
                        "\"message\": \"%s\"," +
                        "\"status\": %d," +
                        "\"method\": \"%s\"," +
                        "\"endpoint\": \"%s\"," +
                        "\"userAgent\": \"%s\"," +
                        "\"timestamp\": \"%s\"" +
                        "}",
                authException.getMessage(),
                HttpServletResponse.SC_UNAUTHORIZED,
                request.getMethod(),
                request.getRequestURI(),
                userAgent,
                timestamp
        );

        // Escrever a resposta
        response.getWriter().write(jsonResponse);
    }
}