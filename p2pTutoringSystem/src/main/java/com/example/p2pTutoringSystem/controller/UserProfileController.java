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
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController()
@RequestMapping("api/v1/profile-picture")
@AllArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    private static final String UPLOAD_DIR = "uploads/";
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

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

    @PostMapping("/upload/{userId}")
    public ResponseEntity<?> uploadOrUpdateProfileImage(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file uploaded");
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

            Optional<User> userOptional = userRepository.findByUserId(userId);
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }

            User user = userOptional.get();

            Optional<UserProfile> profileOptional = userProfileService.checkProfileByUser(user);

            UserProfile profile;
            if (profileOptional.isPresent()) {
                profile = profileOptional.get();
                profile.setFilePath("/uploads/" + fileName);
            } else {
                profile = new UserProfile(user, "/uploads/" + fileName);
            }

            userProfileRepository.save(profile);

            return ResponseEntity.ok(Map.of(
                    "message", "Profile image saved successfully",
                    "filePath", profile.getFilePath()
            ));

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error saving file");
        }
    }






}
