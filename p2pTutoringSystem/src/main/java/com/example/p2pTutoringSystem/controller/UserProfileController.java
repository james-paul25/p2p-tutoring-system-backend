package com.example.p2pTutoringSystem.controller;

import com.example.p2pTutoringSystem.entities.User;
import com.example.p2pTutoringSystem.entities.UserProfile;
import com.example.p2pTutoringSystem.repositories.UserProfileRepository;
import com.example.p2pTutoringSystem.repositories.UserRepository;
import com.example.p2pTutoringSystem.services.UserProfileService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController()
@RequestMapping("api/v1/profile-picture")
@AllArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    private static final String UPLOAD_DIR = "uploads/";
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<?> uploadProfileImage(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file uploaded");
            }

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Optional<User> userOptional = userRepository.findByUserId(userId);
            if (!userOptional.isPresent()) { return ResponseEntity.badRequest().body("User not found"); }
            User user = userOptional.get();

            Optional<UserProfile> existingProfile = userProfileService.checkProfileByUser(user);
            if (existingProfile.isPresent()) {
                return ResponseEntity.badRequest().body("You already uploaded a profile image");
            }

            UserProfile profile = new UserProfile(user, "/uploads/" + fileName);
            UserProfile savedProfile = userProfileService.saveUserProfile(profile);

            return ResponseEntity.ok(savedProfile);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error saving file");
        }
    }

    @GetMapping("/get-profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        Optional<UserProfile> profileOpt = userProfileService.getProfileByUserId(userId);
        return profileOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-all-profiles")
    public ResponseEntity<?> getAllUserProfiles() {
        return ResponseEntity.ok(userProfileService.getAllUserProfiles());
    }

    @PutMapping("/edit-profile/{profileId}")
    public ResponseEntity<?> editProfilePicture(
            @PathVariable Long profileId,
            @RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded.");
        }

        if (!file.getContentType().startsWith("image/")) {
            return ResponseEntity.badRequest().body("Only image files are allowed.");
        }

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        Optional<UserProfile> userProfileOptional = userProfileRepository.findByProfileId(profileId);
        if (userProfileOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Profile ID not found.");
        }

        UserProfile userProfile = userProfileOptional.get();
        userProfile.setFilePath("/uploads/" + fileName);
        userProfileRepository.save(userProfile);

        return ResponseEntity.ok("Profile picture updated successfully.");
    }


}
