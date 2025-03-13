package com.example.tela_login.Service;

import com.example.tela_login.DTO.LoginRequestDTO;
import com.example.tela_login.DTO.LoginResponseDTO;
import com.example.tela_login.Model.UsuarioModel;
import com.example.tela_login.Repository.UsuarioRepository;
import com.example.tela_login.Util.AuthUtil;
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
        // Valida se os campos foram preenchidos
        LoginResponseDTO validacaoCampos = validarCampos(dto);
        if (validacaoCampos != null) return validacaoCampos;

        // Busca o usuário no banco
        UsuarioModel user = buscarUsuario(dto.usuario());
        if (user == null) return new LoginResponseDTO("Usuário não encontrado", null);

        // Verifica se o usuário está bloqueado
//        LoginResponseDTO bloqueioResponse = verificarTentativasBloqueio(user);
//        if (bloqueioResponse != null) return bloqueioResponse;

        // Valida a senha
        LoginResponseDTO senhaResponse = validarSenha(dto.senha(), user);
        if (senhaResponse != null) return senhaResponse;

        // Se o login for bem-sucedido, reseta as tentativas
//        resetarTentativas(user);

        return new LoginResponseDTO("Login bem-sucedido!", "/home");
    }

    private LoginResponseDTO validarCampos(LoginRequestDTO dto) {
        //Valida se o usuario esta em branco
        if (dto.usuario() == null || dto.usuario().isEmpty()) {
            return new LoginResponseDTO("Usuário não pode estar em branco.", null);
        }
        //Valida se a senha esta em branco
        if (dto.senha() == null || dto.senha().isEmpty()) {
            return new LoginResponseDTO("Senha não pode estar em branco.", null);
        }
//Valida se o usuario contem caracteres especiais
        if (AuthUtil.contemCaracteresEspeciais(dto.usuario())) {
            return new LoginResponseDTO("Usuário não pode conter caracteres especiais.", null);
        }
        //Valida se o usuario contem caracteres repetidos em sequencia como ggg aaa etc
        if (AuthUtil.contemRepeticaoExcessiva(dto.usuario())) {
            return new LoginResponseDTO("Usuario não pode conter caracteres repetidos sequencialmente.", null);
        }

        return null;
    }

    private UsuarioModel buscarUsuario(String usuario) {
        return userRepository.findByUsuario(usuario).orElse(null);
    }

//    private LoginResponseDTO verificarTentativasBloqueio(UsuarioModel user) {
//        if (user.getDataDesbloqueio() != null && LocalDateTime.now().isBefore(user.getDataDesbloqueio())) {
//            return new LoginResponseDTO("Usuário bloqueado. Tente novamente após: " + user.getDataDesbloqueio(), null);
//        }
//        return null;
//    }

    private LoginResponseDTO validarSenha(String senha, UsuarioModel user) {
        if (!passwordMatches(senha, user.getSenha())) {
            //incrementarTentativas(user);
            return new LoginResponseDTO("Senha incorreta.", null);
        }
        return null;
    }

//    private void incrementarTentativas(UsuarioModel user) {
//        user.setTentativasFalhas(user.getTentativasFalhas() + 1);
//
//        if (user.getTentativasFalhas() >= 3) {
//            user.setDataDesbloqueio(LocalDateTime.now().plusDays(3));
//            user.setTentativasFalhas(0);
//            userRepository.save(user);
//        } else {
//            userRepository.save(user);
//        }
//    }
//
//    private void resetarTentativas(UsuarioModel user) {
//        user.setTentativasFalhas(0);
//        user.setDataDesbloqueio(null);
//        userRepository.save(user);
//    }

    private boolean passwordMatches(String senhaFornecida, String senhaArmazenada) {
        return senhaFornecida.equals(senhaArmazenada); // Substituir por bcrypt se necessário
    }
}

