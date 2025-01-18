/** Clasa pentru gestionarea vehiculelor
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus.Controller;

import com.example.AplicatieBus.Model.Vehicule;
import com.example.AplicatieBus.Service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class VehiculeController {

    @Autowired
    private VehiculeService vehiculeService;

    @GetMapping("/vehicule")
    public String showAllVehicule(Model model) {
        List<Vehicule> vehiculeList = vehiculeService.getAllVehicule(); // Obține lista de vehicule
        model.addAttribute("vehicule", vehiculeList); // Adaugă lista în model
        return "vehicule"; // Numele șablonului Thymeleaf (vehicule.html)
    }

    @GetMapping("/adaugarevehicul")
    public String showAdaugareVehiculForm(Model model) {
        model.addAttribute("vehicul", new Vehicule());
        return "adaugarevehicul";
    }

    @PostMapping("/adaugarevehicul")
    public String addVehicul(@ModelAttribute Vehicule vehicul,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        String nrInmatriculare = vehicul.getNrInmatriculare();
        // Definirea structura numar de inmatriculare
        String regex = "^(B\\d{3}[A-Z]{3}|[A-Z]{1,2}\\d{2}[A-Z]{3})$";

        // Verificare dacă numărul de înmatriculare există deja
        if (vehiculeService.existsByNrInmatriculare(nrInmatriculare)) {
            model.addAttribute("errorMessage", "Numărul de înmatriculare există deja!");
            return "adaugarevehicul";  // Rămâne pe aceeași pagină pentru afișarea erorii
        }

        // Verificare dacă numărul de înmatriculare respectă structura specificată
        if (!nrInmatriculare.matches(regex)) {
            model.addAttribute("errorMessage", "Numărul de înmatriculare este invalid!");
            return "adaugarevehicul";  // Rămâne pe aceeași pagină pentru afișarea erorii
        }

        // Salvează vehiculul dacă toate validările au trecut
        vehiculeService.saveVehicul(vehicul);
        redirectAttributes.addFlashAttribute("successMessage", "Vehiculul a fost adăugat cu succes!");
        return "redirect:/vehicule";
    }


    @GetMapping("/editarevehicul")
    public String showEditareVehicul(Model model) {
        List<Vehicule> vehicule = vehiculeService.getVehiculeDisponibile(null, null);
        model.addAttribute("vehiculeDisponibile", vehicule);
        return "editarevehicul";
    }

    @PostMapping("/editarevehicul/select")
    public String selectVehicul(@RequestParam int vehiculId, Model model) {
        Vehicule vehicul = vehiculeService.getVehiculById(vehiculId);
        model.addAttribute("vehicul", vehicul);
        List<Vehicule> vehicule = vehiculeService.getVehiculeDisponibile(null, null);
        model.addAttribute("vehiculeDisponibile", vehicule);
        return "editarevehicul";
    }

    @DeleteMapping("/editarevehicul/delete/{id}")
    public ResponseEntity<Void> deleteVehicul(@PathVariable int id) {
        vehiculeService.deleteVehicul(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/editarevehicul/save")
    public String saveVehicul(@ModelAttribute Vehicule vehicul) {
        vehiculeService.updateVehicul(vehicul);
        return "redirect:/editarevehicul";
    }


}
