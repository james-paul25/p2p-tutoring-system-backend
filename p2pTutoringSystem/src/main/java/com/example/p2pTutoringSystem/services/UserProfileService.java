package com.example.p2pTutoringSystem.services;

import com.example.p2pTutoringSystem.entities.User;
import com.example.p2pTutoringSystem.entities.UserProfile;
import com.example.p2pTutoringSystem.repositories.UserProfileRepository;
import com.example.p2pTutoringSystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserProfileService {
    private UserProfileRepository userProfileRepository;
    private UserRepository userRepository;

    public UserProfile saveUserProfile(UserProfile profile) {
        return userProfileRepository.save(profile);
    }

    public Optional<UserProfile> getProfileByUserId(Long userId) {
        Optional<User> userProfile = userRepository.findByUserId(userId);
        if(userProfile.isEmpty()) return null;

        User user = userProfile.get();

        return userProfileRepository.findByUser(user);
    }

    public Optional<UserProfile> getProfileById(Long profileId) {
        return userProfileRepository.findById(profileId);
    }

    public Optional<UserProfile> checkProfileByUser(User user) {
        return userProfileRepository.findByUser(user);
    }

    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }
}
