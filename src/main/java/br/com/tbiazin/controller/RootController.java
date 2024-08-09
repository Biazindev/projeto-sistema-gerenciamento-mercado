package br.com.tbiazin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class RootController {

    @GetMapping("/")
    public String root() {
        return "index"; // Nome do arquivo index.html na pasta templates
    }
}
