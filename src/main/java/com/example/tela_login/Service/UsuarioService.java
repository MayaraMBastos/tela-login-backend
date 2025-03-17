package com.example.tela_login.Service;

import com.example.tela_login.DTO.UsuarioRequestDTO;
import com.example.tela_login.DTO.UsuarioResponseDTO;
import com.example.tela_login.Model.UsuarioModel;
import com.example.tela_login.Repository.UsuarioRepository;
import com.example.tela_login.Util.AuthUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDTO authenticateRegistro(UsuarioRequestDTO dto){

        List<String> erros = new ArrayList<>();

        //Verificar se o usuario ja existe no banco
        Optional<UsuarioModel> optionalUser = usuarioRepository.findByUsuario(dto.usuario());
        if (!optionalUser.isEmpty()) {
            erros.add("Nome de usuario indisponivel");
        }

        // Chamando os validadores e adicionando os erros na lista
        String erroUsuario = AuthUtil.validarUsuario(dto.usuario());
        if (erroUsuario != null) erros.add(erroUsuario);

        String erroSenha = AuthUtil.validarSenha(dto.senha());
        if (erroSenha != null) erros.add(erroSenha);

        //Se a confirmacao de senha nao corresponder a senha inserida
        String erroConfSenha = AuthUtil.validarConfirmacaoSenha(dto.senha(), dto.confSenha());
        if (erroConfSenha  != null ) erros.add(erroConfSenha );


        // Se houver erros de validação, retorna imediatamente
        if (!erros.isEmpty()) {
            return new UsuarioResponseDTO(erros, null);
        }

        // Se nao houver erros salva usuario no banco
        if (erros.isEmpty()) {
            salvarRegistro(dto);
        }

        // Caso registro seja bem-sucedido
        return new UsuarioResponseDTO(List.of("Registro bem-sucedido!"), "/login");

    }

    public UsuarioModel salvarRegistro(UsuarioRequestDTO rDto) {
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setUsuario(rDto.usuario());
        usuarioModel.setSenha(passwordEncoder.encode(rDto.senha()));
        return usuarioRepository.save(usuarioModel);

    }


}
