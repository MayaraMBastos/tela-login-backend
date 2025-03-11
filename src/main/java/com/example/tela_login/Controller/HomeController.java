package com.example.tela_login.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://192.168.56.1:3000")
public class HomeController {

    @GetMapping("/home")
    public String pagHome(){
        return "/home";
    }
}
