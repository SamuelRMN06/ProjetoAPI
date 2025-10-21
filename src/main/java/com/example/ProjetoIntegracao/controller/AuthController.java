package com.example.ProjetoIntegracao.controller;

import com.example.ProjetoIntegracao.auth.JwtUtil;
import com.example.ProjetoIntegracao.model.UsuarioModel;
import com.example.ProjetoIntegracao.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioModel> registrar(@RequestBody UsuarioModel usuario) {
        UsuarioModel novoUsuario = usuarioService.registrar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioModel usuario) {
        UsuarioModel autenticado = usuarioService.autenticar(usuario.getEmail(), usuario.getSenha());
        if (autenticado == null) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }

        String token = jwtUtil.generateToken(autenticado.getEmail());
        return ResponseEntity.ok(token);
    }

}
