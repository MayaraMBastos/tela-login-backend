package com.example.tela_login.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @CrossOrigin( origins = "*", allowedHeaders = "*")
    @GetMapping("/home")
    public String pagHome(){
        return "/home";
    }
}
