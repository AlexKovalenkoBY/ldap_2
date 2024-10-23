package com.example.authenticatingldap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedController {

    @GetMapping("/protected-endpoint")
    public String protectedEndpoint() {
        return "This is a protected endpoint";
    }
}