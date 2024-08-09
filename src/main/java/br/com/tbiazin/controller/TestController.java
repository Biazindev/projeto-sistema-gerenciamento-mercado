package br.com.tbiazin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("api/test")
public class TestController {

    @GetMapping
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Endpoint funcionando");
    }
}
