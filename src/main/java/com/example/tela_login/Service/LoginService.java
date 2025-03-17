package com.example.tela_login.Service;

import com.example.tela_login.DTO.LoginRequestDTO;
import com.example.tela_login.DTO.LoginResponseDTO;
import com.example.tela_login.Model.UsuarioModel;
import com.example.tela_login.Repository.UsuarioRepository;
import com.example.tela_login.Util.AuthUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository userRepository;

    private final UsuarioService usuarioService;

    public LoginService(PasswordEncoder passwordEncoder, UsuarioRepository userRepository, UsuarioService usuarioService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.usuarioService = usuarioService;
    }

    public LoginResponseDTO authenticate(LoginRequestDTO dto) {
        List<String> erros = new ArrayList<>();

        // Buscar usuário no banco
        Optional<UsuarioModel> optionalUser = userRepository.findByUsuario(dto.usuario());
        if (optionalUser.isEmpty()) {
            return new LoginResponseDTO(List.of("Usuário não encontrado."), null);
        }

        // Chamando os validadores e adicionando os erros na lista
        String erroUsuario = AuthUtil.validarUsuario(dto.usuario());
        if (erroUsuario != null) erros.add(erroUsuario);

        String erroSenha = AuthUtil.validarSenha(dto.senha());
        if (erroSenha != null) erros.add(erroSenha);

        UsuarioModel user = optionalUser.get();

        // Comparar a senha fornecida com a senha criptografada
        if (!passwordEncoder.matches(dto.senha(), user.getSenha())) {
            return new LoginResponseDTO(List.of("Senha incorreta."), null);
        }

        // Se houver erros de validação, retorna imediatamente
        if (!erros.isEmpty()) {
            return new LoginResponseDTO(erros, null);
        }

        // Caso login seja bem-sucedido
        return new LoginResponseDTO(List.of("Login bem-sucedido!"), "/home");
    }
}
