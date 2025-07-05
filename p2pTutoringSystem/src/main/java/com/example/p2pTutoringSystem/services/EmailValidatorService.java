package com.example.p2pTutoringSystem.services;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidatorService implements Predicate<String> {
    @Override
    public boolean test(String email) {
        return email != null && email.endsWith("@bisu.edu.ph");
    }
}
