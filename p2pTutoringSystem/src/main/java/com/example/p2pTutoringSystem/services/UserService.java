package com.example.p2pTutoringSystem.services;

import com.example.p2pTutoringSystem.Utils.PasswordUtils;
import com.example.p2pTutoringSystem.entities.Student;
import com.example.p2pTutoringSystem.entities.User;
import com.example.p2pTutoringSystem.enumarate.UserRole;
import com.example.p2pTutoringSystem.repositories.StudentRepository;
import com.example.p2pTutoringSystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "username %s not found";
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, username)));
    }

    public String registerUser(User user) {
        Optional<User> userExist = userRepository.findByEmail(user.getEmail());

        if(userExist.isPresent()) {
            return "email already used";
        }

        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);

        if (user.getRole() == UserRole.STUDENT) {
            Student student = new Student(user);
            studentRepository.save(student);
        }


        return "User registered successfully!";
    }

    public boolean isLogin(String email, String rawPassword) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && PasswordUtils.verifyPassword(rawPassword, user.get().getPassword());
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email));
        }
    }

    public User getUserById(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + userId));
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String deleteUserById(Long userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if(!user.isPresent()) { return "User not found"; }
        userRepository.delete(user.get());
        return "User deleted successfully!";
    }
}
