package com.example.tela_login.DTO;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO (
        @NotBlank String usuario,
        @NotBlank String senha){
}
