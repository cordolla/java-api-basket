package com.basket.api.modules.user.useCases;

import com.basket.api.exception.BusinessRuleException;
import com.basket.api.modules.user.entity.UserEntity;
import com.basket.api.modules.user.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity execute(UserEntity userEntity) {
        this.userRepository
                .findByEmail(userEntity.getEmail())
                .ifPresent((user) -> {
                    throw new BusinessRuleException("Email already exists: " + userEntity.getEmail());
                });

        var password = this.passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(password);

        return this.userRepository.save(userEntity);
    }

}
