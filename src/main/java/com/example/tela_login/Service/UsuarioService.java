package com.example.tela_login.Service;

import com.example.tela_login.DTO.UsuarioRequestDTO;
import com.example.tela_login.Model.UsuarioModel;
import com.example.tela_login.Repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioModel salvarRegistro(UsuarioRequestDTO rDto) {
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setUsuario(rDto.usuario());
        usuarioModel.setSenha(passwordEncoder.encode(rDto.senha()));
        return usuarioRepository.save(usuarioModel);

    }


}
