package com.example.jwtcors.controller;

import com.example.jwtcors.dto.LoginRequest;
import com.example.jwtcors.dto.RegisterRequest;
import com.example.jwtcors.entity.Role;
import com.example.jwtcors.entity.User;
import com.example.jwtcors.service.JwtUtil;
import com.example.jwtcors.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController (UserService userService, JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        try {
            User newUser = userService.register(
                    request.getUsername(),
                    request.getPassword(),
                    request.getRole()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar el usuario");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        User u = userService.findByUsername(request.getUsername());

        if(u == null || !userService.checkPassword(request.getPassword(), u.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales invalidas");
        }

        return ResponseEntity.ok(jwtUtil.generateToken(u.getUsername(), u.getRole()));
    }
}
