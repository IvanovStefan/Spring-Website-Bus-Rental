/** Interfata pentru extinderea JPARepository
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus.Repository;

import com.example.AplicatieBus.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username); // Verifică dacă username-ul există
    Optional<User> findByUsername(String username);
}