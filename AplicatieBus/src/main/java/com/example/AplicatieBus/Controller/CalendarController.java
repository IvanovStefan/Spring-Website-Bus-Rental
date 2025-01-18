/** Clasa pentru gestionarea calendarului de programari
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus.Controller;
import com.example.AplicatieBus.Model.EventDTO;

import com.example.AplicatieBus.Service.ProgramareService;
import com.example.AplicatieBus.Service.UserService;
import com.example.AplicatieBus.Service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CalendarController {

    @Autowired
    private ProgramareService programareService;

    @Autowired
    private UserService userService;

    @Autowired
    private VehiculeService vehiculeService;

    @GetMapping("/api/programari")
    @ResponseBody
    public List<EventDTO> getProgramari() {
        return programareService.getAllProgramari().stream()
                .map(programare -> {
                    String inceput = programare.getInceput();
                    String sfarsit = programare.getSfarsit();

                    // Dacă inceput și sfarsit sunt egale, nu incrementăm sfarsit
                    if (inceput.equals(sfarsit)) {
                        return new EventDTO(
                                programare.getIdProgramare(),
                                programare.getDescriere(),
                                inceput,
                                sfarsit
                        );
                    } else {
                        // Incrementăm sfarsit cu o zi pentru a include ultima zi în calendar
                        return new EventDTO(
                                programare.getIdProgramare(),
                                programare.getDescriere(),
                                inceput,
                                LocalDate.parse(sfarsit).plusDays(1).toString()
                        );
                    }
                })
                .collect(Collectors.toList());
    }}