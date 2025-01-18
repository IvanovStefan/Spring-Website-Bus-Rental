/** Clasa pentru gestionarea paginii principale si afisarea calendarului
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @GetMapping("/")
    public String showHomePage(Model model) {
        // Obține utilizatorul autentificat
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Preia numele utilizatorului

        // Adaugă numele utilizatorului în model
        model.addAttribute("username", username);
        return "home"; // Returnează pagina principală
    }

    @GetMapping("/calendar")
    public String showCalendar(Model model) {
        return "calendar"; // Returnează calendarul
    }
}