package com.example.tela_login.Service;

import com.example.tela_login.DTO.LoginRequestDTO;
import com.example.tela_login.DTO.LoginResponseDTO;
import com.example.tela_login.DTO.UsuarioRequestDTO;
import com.example.tela_login.DTO.UsuarioResponseDTO;
import com.example.tela_login.Model.UsuarioModel;
import com.example.tela_login.Repository.UsuarioRepository;
import com.example.tela_login.Util.AuthUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository userRepository;

    private final UsuarioService usuarioService;

    public AuthService(PasswordEncoder passwordEncoder, UsuarioRepository userRepository, UsuarioService usuarioService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.usuarioService = usuarioService;
    }

    public LoginResponseDTO authenticate(LoginRequestDTO dto) {
        List<String> erros = new ArrayList<>();

        // Chamando os validadores e adicionando os erros na lista
        String erroUsuario = AuthUtil.validarUsuario(dto.usuario());
        if (erroUsuario != null) erros.add(erroUsuario);

        String erroSenha = AuthUtil.validarSenha(dto.senha());
        if (erroSenha != null) erros.add(erroSenha);

        // Se houver erros de validação, retorna imediatamente
        if (!erros.isEmpty()) {
            return new LoginResponseDTO(erros, null);
        }

        // Buscar usuário no banco
        Optional<UsuarioModel> optionalUser = userRepository.findByUsuario(dto.usuario());
        if (optionalUser.isEmpty()) {
            return new LoginResponseDTO(List.of("Usuário não encontrado."), null);
        }

        UsuarioModel user = optionalUser.get();

        // Comparar a senha fornecida com a senha criptografada
        if (!passwordMatches(dto.senha(), user.getSenha())) {
            return new LoginResponseDTO(List.of("Senha incorreta."), null);
        }

        // Caso login seja bem-sucedido
        return new LoginResponseDTO(List.of("Login bem-sucedido!"), "/home");
    }

    public UsuarioResponseDTO authenticateRegistro(UsuarioRequestDTO dto){

        List<String> erros = new ArrayList<>();

        // Chamando os validadores e adicionando os erros na lista
        String erroUsuario = AuthUtil.validarUsuario(dto.usuario());
        if (erroUsuario != null) erros.add(erroUsuario);

        String erroSenha = AuthUtil.validarSenha(dto.senha());
        if (erroSenha != null) erros.add(erroSenha);

        //Se a confirmacao de senha nao corresponder a senha inserida
        String erroConfSenha = AuthUtil.validarConfirmacaoSenha(dto.senha(), dto.confSenha());
        if (erroConfSenha  != null ) erros.add(erroConfSenha );

        //Verificar se o usuario ja existe no banco
        if (userRepository.findByUsuario(dto.usuario()) != null){
            erros.add("Nome de usuario indisponivel");
        }

        // Se houver erros de validação, retorna imediatamente
        if (!erros.isEmpty()) {
            return new UsuarioResponseDTO(erros, null);
        }

        // Chamando UsuarioService para salvar no banco
        UsuarioModel usuarioSalvo = usuarioService.salvarRegistro(dto);

        // Caso registro seja bem-sucedido
        return new UsuarioResponseDTO(List.of("Registro bem-sucedido!"), "/login");

    }

    private boolean passwordMatches(String senhaFornecida, String senhaArmazenada) {
        return senhaFornecida.equals(senhaArmazenada); // Substituir por bcrypt se necessário
    }

}


