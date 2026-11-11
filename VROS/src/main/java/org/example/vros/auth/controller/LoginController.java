package org.example.vros.auth.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth", description = "Mock Authentication API")
public class LoginController {

    @Operation(summary = "Mock login endpoint")
    @PostMapping("/login")
    public ResponseEntity<String> mockLogin() {
        return ResponseEntity.ok("Mock login realizat cu succes. Token-ul tău este: dummy-token-12345");
    }
}