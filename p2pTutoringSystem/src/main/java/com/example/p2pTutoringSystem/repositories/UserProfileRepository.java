package com.example.p2pTutoringSystem.repositories;

import com.example.p2pTutoringSystem.entities.User;
import com.example.p2pTutoringSystem.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser(User user);
    Optional<UserProfile> findByProfileId(Long profileId);
}
