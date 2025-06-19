# atendimento-app
Aplicação para atendimentos.

## Requisitos Funcionais da Aplicação:

1. Cadastro de atendimento:
- Nome do cliente
- CPF (opcional, pode ser mascarado)
- Descrição do atendimento
- Tipo (reclamação, sugestão, dúvida, etc.)
- Data/hora
- Número de protocolo (gerado automaticamente)

2. Consulta de atendimentos por CPF ou protocolo

## Requisitos Não Funcionais – Segurança (OWASP Top 10)

Este projeto foi desenvolvido com foco na prevenção de quatro das principais vulnerabilidades da OWASP:

### 🛡️ A01 - Broken Access Control
- Controle de acesso baseado em perfis (roles): USER e ADMIN.
- Endpoints sensíveis como listagem e exclusão de atendimentos são restritos apenas a usuários com perfil ADMIN.
- Spring Security com @PreAuthorize garante que usuários não acessem recursos de outros.

### 🛡️ A03 - Injection
- Todas as interações com o banco são feitas por meio do Spring Data JPA, evitando SQL Injection.
- Parâmetros são tratados com segurança utilizando anotações como @Param e evitando concatenar strings em queries.

### 🛡️ A07 - Identification and Authentication Failures
- Login via autenticação JWT (JSON Web Token).
- JWT transmitido via cookie `HttpOnly`, evitando exposição ao JavaScript do cliente.
- Senhas armazenadas com BCryptPasswordEncoder.
- Tokens possuem validade e não podem ser reutilizados após logout ou expiração.

### 🛡️ A09 - Security Logging and Monitoring Failures
- Log de tentativas de login inválidas.
- Registro de eventos importantes do sistema, como criação e exclusão de atendimentos.
- Log estruturado com nível adequado (INFO, WARN, ERROR) para facilitar auditoria.

