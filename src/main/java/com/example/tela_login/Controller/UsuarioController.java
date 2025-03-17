package com.example.tela_login.Controller;

import com.example.tela_login.DTO.UsuarioRequestDTO;
import com.example.tela_login.DTO.UsuarioResponseDTO;

import com.example.tela_login.Service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://192.168.56.1:3000")
public class UsuarioController {


    private  final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @PostMapping ("/registro")
    public ResponseEntity<UsuarioResponseDTO> registroUsuario(@RequestBody @Valid UsuarioRequestDTO dto) {
        UsuarioResponseDTO responseDTO = usuarioService.authenticateRegistro(dto);
        return ResponseEntity.ok(responseDTO);
    }
}
