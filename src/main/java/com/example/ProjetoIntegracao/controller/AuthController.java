package com.example.ProjetoIntegracao.controller;

import com.example.ProjetoIntegracao.auth.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/gerar-token")
    public String generateToken() {
        return jwtUtil.generateToken();
    }
}