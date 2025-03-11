package com.example.tela_login.Service;

import com.example.tela_login.DTO.LoginRequestDTO;
import com.example.tela_login.DTO.LoginResponseDTO;
import com.example.tela_login.Model.UsuarioModel;
import com.example.tela_login.Repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UsuarioRepository userRepository;

    public AuthService(UsuarioRepository userRepository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
    }

    public LoginResponseDTO authenticate(LoginRequestDTO dto) {
        // Buscar usuário no banco
        UsuarioModel user = userRepository.findByUsuario(dto.usuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Comparar a senha fornecida com a senha criptografada
        if (!passwordMatches(dto.senha(), user.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        // Aqui você pode retornar um token ou informações relacionadas ao login
        return new LoginResponseDTO(dto.usuario(),"Login bem sucedido");
    }

    private boolean passwordMatches(String plainPassword, String encryptedPassword) {
        return passwordEncoder.matches(plainPassword, encryptedPassword);
    }
}

