package com.example.p2pTutoringSystem.services;

import com.example.p2pTutoringSystem.dto.UserRegistrationRequest;
import com.example.p2pTutoringSystem.entities.User;
import com.example.p2pTutoringSystem.enumarate.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRegistrationService {

    private final UserService userService;
    private final EmailValidatorService emailValidatorService;

    public String registerUser(UserRegistrationRequest registrationRequest) {
       boolean isValidEmail = emailValidatorService.test(registrationRequest.getEmail());

       if(isValidEmail) {
           String result = userService.registerUser(
                   new User(
                           registrationRequest.getUsername(),
                           registrationRequest.getEmail(),
                           registrationRequest.getPassword(),
                           UserRole.STUDENT
                   ));

           return result.isEmpty() ? "User registered successfully!" : result;

       } else {
           //throw new IllegalStateException("please use your BISU email");
           return "please use your BISU email";
       }
    }


}
