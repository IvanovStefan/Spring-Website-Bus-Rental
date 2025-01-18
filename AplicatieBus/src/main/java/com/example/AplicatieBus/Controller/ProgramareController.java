/** Clasa pentru gestionarea rezervarilor si detaliilor acestora
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus.Controller;

import com.example.AplicatieBus.Model.Programare;
import com.example.AplicatieBus.Model.User;
import com.example.AplicatieBus.Model.Vehicule;
import com.example.AplicatieBus.Service.ProgramareService;
import com.example.AplicatieBus.Service.UserService;
import com.example.AplicatieBus.Service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProgramareController {

    @Autowired
    private ProgramareService programareService;

    @Autowired
    private VehiculeService vehiculService;

    @Autowired
    private UserService userService;

    @GetMapping("/programari")
    public String showAllProgramari(Model model) {
        List<Programare> programareList = programareService.getAllProgramari(); // Obține lista de programări
        model.addAttribute("programari", programareList); // Adaugă lista în model
        return "programari"; // Numele șablonului Thymeleaf (programari.html)
    }

    @GetMapping("/programare/{id}")
    public String getProgramareById(@PathVariable int id, Model model, Principal principal) {
        Programare programare = programareService.getProgramareById(id);
        Vehicule vehicul = vehiculService.getVehiculById(programare.getIdVehicul());
        User user = userService.getUserById(programare.getIdUser());

        model.addAttribute("programare", programare);
        model.addAttribute("vehicul", vehicul);
        model.addAttribute("user", user);
        model.addAttribute("loggedInUsername", principal.getName());
        return "calendar-detaliu";
    }

    @GetMapping("/editareinchiriere/{id}")
    public String editareInchiriere(@PathVariable int id, Model model) {
        Programare programare = programareService.getProgramareById(id);
        Vehicule vehicul = vehiculService.getVehiculById(programare.getIdVehicul());

        model.addAttribute("programare", programare);
        model.addAttribute("vehicul", vehicul);
        return "editare-inchiriere";
    }

    @PostMapping("/editareinchiriere/{id}")
    public String modificaInchiriere(@PathVariable int id,
                                     @ModelAttribute Programare programare,
                                     RedirectAttributes redirectAttributes,
                                     Principal principal) {
        // Obține programarea existentă și proprietarul acesteia
        Programare existingProgramare = programareService.getProgramareById(id);
        User owner = userService.getUserById(existingProgramare.getIdUser());

        // Obține numele utilizatorului autentificat
        String loggedInUsername = principal.getName();

        // Verifică dacă utilizatorul autentificat este proprietarul programării
        if (!loggedInUsername.equals(owner.getUsername())) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Nu ai permisiunea de a modifica această programare!");
            return "redirect:/calendar"; // Redirecționează în cazul accesului neautorizat
        }

        // Validare număr de telefon: trebuie să conțină doar cifre
        String telefon = programare.getTelefon();
        if (telefon == null || !telefon.matches("\\d+")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Numărul de telefon este invalid. Introdu doar cifre.");
            return "redirect:/editareinchiriere/" + id;
        }
        programareService.updateProgramare(id, programare);
        redirectAttributes.addFlashAttribute("successMessage", "Programarea a fost modificată cu succes!");
        return "redirect:/calendar";
    }

    @PostMapping("/stergereprogramare/{idProgramare}")
    public String stergereProgramare(@PathVariable int idProgramare,
                                     RedirectAttributes redirectAttributes,
                                     Principal principal) {
        // Obține programarea existentă și proprietarul acesteia
        Programare programare = programareService.getProgramareById(idProgramare);
        User owner = userService.getUserById(programare.getIdUser());

        // Obține numele utilizatorului autentificat
        String loggedInUsername = principal.getName();

        // Verifică dacă utilizatorul autentificat este proprietarul programării
        if (!loggedInUsername.equals(owner.getUsername())) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Nu ai permisiunea de a șterge această programare!");
            return "redirect:/calendar"; // Redirecționează în cazul accesului neautorizat
        }

        // Șterge programarea dacă verificarea trece
        programareService.deleteProgramareById(idProgramare);
        redirectAttributes.addFlashAttribute("successMessage", "Programarea a fost ștearsă cu succes!");
        return "redirect:/calendar";
    }



}
