package com.basket.api.modules.user.useCases;

import com.basket.api.modules.user.entity.UserEntity;
import com.basket.api.modules.user.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity execute(UserEntity userEntity) {
        this.userRepository
                .findByEmail(userEntity.getEmail())
                .ifPresent((user) -> {
                    throw new Error("Email already exists");
                });

        var password = this.passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(password);

        return this.userRepository.save(userEntity);
    }

}
