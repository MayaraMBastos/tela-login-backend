package com.example.tela_login.Service;

import com.example.tela_login.DTO.LoginRequestDTO;
import com.example.tela_login.DTO.LoginResponseDTO;
import com.example.tela_login.Model.UsuarioModel;
import com.example.tela_login.Repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<UsuarioModel> optionalUser = userRepository.findByUsuario(dto.usuario());

        if (optionalUser.isEmpty()) {
            return new LoginResponseDTO("Usuário não encontrado", null);
        }

        UsuarioModel user = optionalUser.get();

        // Comparar a senha fornecida com a senha criptografada
        if (!passwordMatches(dto.senha(), user.getSenha())) {
            return new LoginResponseDTO("Senha incorreta", null);
        }

        // Caso login seja bem-sucedido
        return new LoginResponseDTO("Login bem-sucedido!", "/home");
    }

    private boolean passwordMatches(String plainPassword, String encryptedPassword) {
        return passwordEncoder.matches(plainPassword, encryptedPassword);
    }
}

