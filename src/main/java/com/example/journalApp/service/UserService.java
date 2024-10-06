package com.example.journalApp.service;

import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<?> saveNewUser(User user) {
        try {
            // Encoding password
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Setting default role
            user.setRoles(Arrays.asList("USER"));

            // Saving user
            User newUser = userRepository.save(user);

            // Returning created response
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            // Handling unique constraint violation
            if (e.getMostSpecificCause().getMessage().contains("userName")) {
                log.error("User creation failed: userName already exists", e);
                return new ResponseEntity<>("Username already taken. Please choose a different username.", HttpStatus.CONFLICT);
            }
            if (e.getMostSpecificCause().getMessage().contains("email")) {
                log.error("User creation failed: email already exists", e);
                return new ResponseEntity<>("An account with this email already exists.", HttpStatus.CONFLICT);
            }
            log.error("User creation failed: data integrity violation", e);
            return new ResponseEntity<>("Data integrity violation occurred.", HttpStatus.CONFLICT);

        } catch (ConstraintViolationException e) {
            // Handling missing or null fields
            log.error("User creation failed: constraint violation", e);
            return new ResponseEntity<>("Invalid user data provided. Ensure all required fields are filled.", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // Catching any other unexpected errors
            log.error("Unexpected error during user creation", e);
            return new ResponseEntity<>("An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public void saveAdmin(String userName) {
        User user = userRepository.findByUserName(userName)
                        .orElseThrow(() -> new UsernameNotFoundException("Username not found for: " + userName));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found for: " + userName));

        return user;
    }
}