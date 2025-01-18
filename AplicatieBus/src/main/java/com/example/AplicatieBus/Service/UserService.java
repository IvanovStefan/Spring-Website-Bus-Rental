/** Clasa pentru gestionarea metodelor clasei User
 * @author Ivanov Stefan
 * @version 12 ianuarie 2025
 */
package com.example.AplicatieBus.Service;

import com.example.AplicatieBus.Repository.UserRepository;
import com.example.AplicatieBus.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public void registerUser(User user) {
        // Criptează parola înainte de salvare
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Salvează utilizatorul în baza de date
        userRepository.save(user);
    }

    public User getUserById(int id) {
        return userRepository.findById((long) id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));
    }
}
