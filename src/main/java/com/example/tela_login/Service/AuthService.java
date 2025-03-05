package com.example.tela_login.Service;

import com.example.tela_login.DTO.LoginRequestDTO;
import com.example.tela_login.DTO.LoginResponseDTO;
import com.example.tela_login.Model.UsuarioModel;
import com.example.tela_login.Repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository userRepository;

    public AuthService(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponseDTO authenticate(LoginRequestDTO dto) {
        UsuarioModel user = userRepository.findByUsuario(dto.usuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se a senha está correta
        if (!passwordMatches(dto.senha(), user.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        return new LoginResponseDTO( user.getUsuario(), "Login bem-sucedido!");
    }

    private boolean passwordMatches(String rawPassword, String encryptedPassword) {
        // Se a senha estiver criptografada no banco, use BCrypt para comparar
        return new BCryptPasswordEncoder().matches(rawPassword, encryptedPassword);
    }
}

