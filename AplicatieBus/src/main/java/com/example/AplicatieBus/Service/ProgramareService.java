/** Clasa pentru gestionarea metodelor clasei Programare
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus.Service;

import com.example.AplicatieBus.Model.Programare;
import com.example.AplicatieBus.Repository.ProgramareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramareService {

    @Autowired
    private ProgramareRepository programareRepository;

    public List<Programare> getAllProgramari() {
        return programareRepository.findAll();
    }

    public Programare getProgramareById(int id) {
        return programareRepository.findById(id).orElse(null);
    }

    public void deleteProgramare(int id) {
        programareRepository.deleteById(id);
    }

    public void saveProgramare(Programare programare) {
        // Verifică dacă există deja o programare similară
        boolean exists = programareRepository.existsByIdUserAndIdVehiculAndInceputAndSfarsit(
                programare.getIdUser(),
                programare.getIdVehicul(),
                programare.getInceput(),
                programare.getSfarsit()
        );

        if (exists) {
            throw new IllegalArgumentException("O programare similară există deja în baza de date!");
        }

        // Validare: Verifică dacă vehiculul este deja rezervat pentru perioada selectată
        boolean isVehiculOcupat = programareRepository.existsByIdVehiculAndPerioada(
                programare.getIdVehicul(),
                programare.getInceput(),
                programare.getSfarsit()
        );

        if (isVehiculOcupat) {
            throw new IllegalArgumentException("Vehiculul selectat este deja rezervat pentru perioada specificată!");
        }

        programareRepository.save(programare);
    }

    public void updateProgramare(int id, Programare programare) {
        Programare existingProgramare = programareRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Programarea nu există!"));

        existingProgramare.setNume(programare.getNume());
        existingProgramare.setPrenume(programare.getPrenume());
        existingProgramare.setTelefon(programare.getTelefon());
        existingProgramare.setDescriere(programare.getDescriere());
        existingProgramare.setKilometri(programare.getKilometri());

        programareRepository.save(existingProgramare);
    }

    public void deleteProgramareById(int idProgramare) {
        programareRepository.deleteById(idProgramare);
    }

}
