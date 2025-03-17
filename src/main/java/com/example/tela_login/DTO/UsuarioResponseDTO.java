package com.example.tela_login.DTO;

import java.util.List;

public record UsuarioResponseDTO(
//mensagens de resposta para requisicoes e validacoes na rota /resgistro
        List<String>mensagens,
        // url para redirecionamento em logins bem sucedidos ou mal sucedidos
        String redirectUrl
) {

}

