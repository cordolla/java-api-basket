package com.basket.api.modules.user.controller;


import com.basket.api.modules.user.records.AuthUserRequestDTO;
import com.basket.api.modules.user.records.AuthUserResponseDTO;
import com.basket.api.modules.user.useCases.AuthUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoint para autenticação de usuários")
public class AuthUserController {


    private final AuthUserUseCase authUserUseCase;

    public AuthUserController(AuthUserUseCase authUserUseCase) {
        this.authUserUseCase = authUserUseCase;
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Autentica um usuário", description = "Realiza a autenticação de um usuário com email e senha, retornando um token JWT em caso de sucesso.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida",
                    content = @Content(schema = @Schema(implementation = AuthUserResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content)
    })
    public ResponseEntity<Object> signIn(@Valid @RequestBody AuthUserRequestDTO authUserRequestDTO) {
        try {
            var token = this.authUserUseCase.execute(authUserRequestDTO);
            return ResponseEntity.ok().body(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
