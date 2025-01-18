/** Clasa pentru gestionarea inregistrarii noilor utilizatori
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus.Controller;

import com.example.AplicatieBus.Service.UserService;
import com.example.AplicatieBus.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User()); // Obiect gol pentru formular
        return "register"; // Numele șablonului Thymeleaf
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user, Model model) {
        if (userService.usernameExists(user.getUsername())) {
            model.addAttribute("error", "Numele de utilizator este deja folosit.");
            return "register";
        }

        userService.registerUser(user); // Salvează utilizatorul
        return "redirect:/login"; // Redirecționează către pagina de login
    }
}
