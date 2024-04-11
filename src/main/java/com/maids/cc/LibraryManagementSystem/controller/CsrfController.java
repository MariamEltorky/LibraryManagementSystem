package com.maids.cc.LibraryManagementSystem.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.web.csrf.CsrfToken;

@RestController
@RequestMapping("/csrf")
public class CsrfController {

    @GetMapping
    public CsrfToken getCsrfToken(CsrfToken token) {
        return token;
    }
}
