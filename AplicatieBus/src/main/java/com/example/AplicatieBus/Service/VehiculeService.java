/** Clasa pentru gestionarea metodelor clasei Vehicule
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus.Service;

import com.example.AplicatieBus.Model.Vehicule;
import com.example.AplicatieBus.Repository.ProgramareRepository;
import com.example.AplicatieBus.Repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculeService {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private ProgramareRepository programareRepository;

    public List<Vehicule> getAllVehicule() {
        return vehiculeRepository.findAll();
    }

    public Vehicule getVehiculById(Integer id) {
        return vehiculeRepository.findById(id).orElse(null);
    }

    public Vehicule saveVehicul(Vehicule vehicul) {
        return vehiculeRepository.save(vehicul);
    }

    public void deleteVehicul(Integer id) {
        vehiculeRepository.deleteById(id);
    }

    public void updateVehicul(Vehicule vehicul) {
        vehiculeRepository.save(vehicul);
    }

    public List<Vehicule> getVehiculeDisponibile(String inceput, String sfarsit) {
        List<Integer> vehiculeOcupate = programareRepository.findVehiculeOcupate(inceput, sfarsit);
        return vehiculeRepository.findAll().stream()
                .filter(vehicul -> !vehiculeOcupate.contains(vehicul.getIdVehicul()))
                .sorted((v1, v2) -> Integer.compare(v1.getNrLocuri(), v2.getNrLocuri())) // Sortează crescător după nr_locuri
                .collect(Collectors.toList());
    }

    public boolean existsByNrInmatriculare(String nrInmatriculare) {
        return vehiculeRepository.existsByNrInmatriculare(nrInmatriculare);
    }

}
