/** Clasa pentru gestionarea functionalitatii de inchiriere a vehiculelor
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus.Controller;

import com.example.AplicatieBus.Model.Programare;
import com.example.AplicatieBus.Model.User;
import com.example.AplicatieBus.Model.Vehicule;
import com.example.AplicatieBus.Service.ProgramareService;
import com.example.AplicatieBus.Service.VehiculeService;
import com.example.AplicatieBus.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/inchiriere")
public class InchiriereController {

    @Autowired
    private ProgramareService programareService;

    @Autowired
    private VehiculeService vehiculService;

    @Autowired
    private UserService userService;

    // Afișează pagina de închiriere
    @GetMapping
    public String showInchirierePage() {
        return "inchiriere"; // Returnează pagina Thymeleaf
    }

    // Obține vehiculele disponibile pentru perioada selectată
    @PostMapping("/vehicule-disponibile")
    @ResponseBody
    public List<Vehicule> getVehiculeDisponibile(@RequestParam String inceput, @RequestParam String sfarsit) {
        return vehiculService.getVehiculeDisponibile(inceput, sfarsit);
    }

    // Salvează programarea
    @PostMapping("/salveaza")
    public String salveazaProgramare(@ModelAttribute Programare programare, Model model, RedirectAttributes redirectAttributes) {
        // Validare: Data de început să fie mai mică sau egală cu data de sfârșit
        if (programare.getInceput().compareTo(programare.getSfarsit()) > 0) {
            model.addAttribute("errorMessage", "Data de început nu poate fi mai mare decât data de sfârșit!");
            return "redirect:/inchiriere"; // Returnează pagina cu un mesaj de eroare
        }

        // Validare număr de telefon: asigură-te că conține doar cifre
        String telefon = programare.getTelefon();
        if (telefon == null || !telefon.matches("\\d+")) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Numărul de telefon este invalid. Introdu doar cifre.");
            return "redirect:/inchiriere";
        }

        // Obține utilizatorul curent
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Obține ID-ul utilizatorului din baza de date
        User user = userService.getUserByUsername(currentUsername);
        programare.setIdUser(user.getId().intValue());

        // Salvează programarea
        programareService.saveProgramare(programare);

        redirectAttributes.addFlashAttribute("successMessage", "Programarea a fost salvată cu succes pentru perioada "
                + programare.getInceput() + " - " + programare.getSfarsit() + "!");
        return "redirect:/inchiriere"; // Returnează pagina după salvare
    }
}