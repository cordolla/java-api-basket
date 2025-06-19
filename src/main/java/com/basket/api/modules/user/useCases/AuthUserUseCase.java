package com.basket.api.modules.user.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.basket.api.modules.user.dto.AuthUserRequestDTO;
import com.basket.api.modules.user.dto.AuthUserResponseDTO;
import com.basket.api.modules.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;

@Service
public class AuthUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret}")
    private String secretKey;

    public AuthUserResponseDTO execute(AuthUserRequestDTO authUserRequestDTO) throws AuthenticationException {
        var user = this.userRepository.findByEmail(authUserRequestDTO.email()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Username/password invalido");
        });

        var passwordMatches = passwordEncoder.matches(authUserRequestDTO.password(), user.getPassword());

        if(!passwordMatches) {
            throw new AuthenticationException();
        }

        var expiresIn = Instant.now().plus(Duration.ofDays(1));
        Algorithm  algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("league").withExpiresAt(expiresIn).withSubject(user.getId().toString()).sign(algorithm);

        return AuthUserResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

    }
}
