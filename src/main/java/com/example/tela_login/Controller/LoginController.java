package com.example.tela_login.Controller;

import com.example.tela_login.DTO.LoginRequestDTO;
import com.example.tela_login.DTO.LoginResponseDTO;
import com.example.tela_login.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://192.168.56.1:3000")
public class LoginController {

    @Autowired
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String pagLogin(){
        return "/login";
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        // Servico de autenticacao de login de usuario; retorna respostas das validacoes e urls de redirecionamentos
        LoginResponseDTO response = authService.authenticate(dto);
        return ResponseEntity.ok(response);
    }
}
