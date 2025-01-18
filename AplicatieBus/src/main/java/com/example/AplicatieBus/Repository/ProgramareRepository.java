/** Interfata pentru extinderea JPARepository
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus.Repository;

import com.example.AplicatieBus.Model.Programare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramareRepository extends JpaRepository<Programare, Integer> {

    @Query("SELECT p.idVehicul FROM Programare p WHERE " +
            "(p.inceput <= :sfarsit AND p.sfarsit >= :inceput)")
    List<Integer> findVehiculeOcupate(String inceput, String sfarsit);

    boolean existsByIdUserAndIdVehiculAndInceputAndSfarsit(int idUser, int idVehicul, String inceput, String sfarsit);

    // Verifică dacă vehiculul este deja rezervat pentru perioada specificată
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Programare p " +
            "WHERE p.idVehicul = :idVehicul " +
            "AND ((:inceput BETWEEN p.inceput AND p.sfarsit) OR (:sfarsit BETWEEN p.inceput AND p.sfarsit) " +
            "OR (p.inceput BETWEEN :inceput AND :sfarsit) OR (p.sfarsit BETWEEN :inceput AND :sfarsit))")
    boolean existsByIdVehiculAndPerioada(int idVehicul, String inceput, String sfarsit);
}
