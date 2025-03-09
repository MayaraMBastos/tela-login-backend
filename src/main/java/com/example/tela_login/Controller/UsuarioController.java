package com.example.tela_login.Controller;

import com.example.tela_login.DTO.UsuarioRequestDTO;
import com.example.tela_login.DTO.UsuarioResponseDTO;
import com.example.tela_login.Model.UsuarioModel;
import com.example.tela_login.Repository.UsuarioRepository;
import com.example.tela_login.Service.UsuarioService;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @CrossOrigin( origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registroUsuario(@RequestBody @Valid UsuarioRequestDTO dto) {
        UsuarioModel usuarioModel = usuarioService.salvarRegistro(dto);
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(usuarioModel.getId(), usuarioModel.getUsuario(), usuarioModel.getSenha());
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponseDTO);
    }
}
