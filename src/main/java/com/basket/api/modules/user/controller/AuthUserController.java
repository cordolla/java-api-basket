package com.basket.api.modules.user.controller;


import com.basket.api.modules.user.dto.AuthUserRequestDTO;
import com.basket.api.modules.user.useCases.AuthUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthUserController {


    private final AuthUserUseCase authUserUseCase;

    public AuthUserController(AuthUserUseCase authUserUseCase) {
        this.authUserUseCase = authUserUseCase;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Object> signIn(@Valid @RequestBody AuthUserRequestDTO authUserRequestDTO) {
        try {
            var token = this.authUserUseCase.execute(authUserRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
