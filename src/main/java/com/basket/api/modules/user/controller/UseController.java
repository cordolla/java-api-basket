package com.basket.api.modules.user.controller;

import com.basket.api.modules.user.entity.UserEntity;
import com.basket.api.modules.user.useCases.CreateUserUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UseController {

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserEntity userEntity) {
        try {
            var result = this.createUserUseCase.execute(userEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
