/** Clasa pentru gestionarea autentificarii utilizatorilor
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Returnează șablonul Thymeleaf pentru login
    }
}
