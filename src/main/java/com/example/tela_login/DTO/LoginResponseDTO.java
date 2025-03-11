package com.example.tela_login.DTO;

public record LoginResponseDTO(
        //mensagem de resposta para requisicoes na rota /login
        String mensagem,
        // url para redirecionamento em logins bem sucedidos ou mal sucedidos
        String redirectUrl
) {
}

