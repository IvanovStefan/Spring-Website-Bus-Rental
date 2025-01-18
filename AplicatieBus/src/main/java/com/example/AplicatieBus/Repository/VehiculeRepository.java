/** Interfata pentru extinderea JPARepository
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus.Repository;


import com.example.AplicatieBus.Model.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {
    boolean existsByNrInmatriculare(String nrInmatriculare);
}
