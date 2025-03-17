package com.example.tela_login.Controller;

import com.example.tela_login.DTO.UsuarioRequestDTO;
import com.example.tela_login.DTO.UsuarioResponseDTO;
import com.example.tela_login.Model.UsuarioModel;
import com.example.tela_login.Repository.UsuarioRepository;
import com.example.tela_login.Service.AuthService;
import com.example.tela_login.Service.UsuarioService;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://192.168.56.1:3000")
public class UsuarioController {


    private  final AuthService authService;

    public UsuarioController(AuthService authService) {
        this.authService = authService;
    }

 @GetMapping("/registro")
    public String pagRegistro(){
        return "/registro";
    }
    @PostMapping ("/resgistro")
    public ResponseEntity<UsuarioResponseDTO> registroUsuario(@RequestBody @Valid UsuarioRequestDTO dto) {
        UsuarioResponseDTO responseDTO = authService.authenticateRegistro(dto);
        return ResponseEntity.ok(responseDTO);
    }
}
