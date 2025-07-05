package com.example.p2pTutoringSystem.services;

import com.example.p2pTutoringSystem.Utils.PasswordUtils;
import com.example.p2pTutoringSystem.dto.AdminRegistrationRequest;
import com.example.p2pTutoringSystem.entities.Admin;
import com.example.p2pTutoringSystem.repositories.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {
    private AdminRepository adminRepository;
    private final EmailValidatorService emailValidatorService;
    private static final String ADMIN_NOT_FOUND_MSG = "Admin %s not found";

    public String registerAdmin(Admin admin) {
        Optional<Admin> userExist = adminRepository.findByEmail(admin.getEmail());

        if(userExist.isPresent()) {
            return "email already used";
        }

        String hashedPassword = PasswordUtils.hashPassword(admin.getPassword());
        admin.setPassword(hashedPassword);
        adminRepository.save(admin);


        return "Admin registered successfully!";
    }

    public String register(AdminRegistrationRequest adminRegistrationRequest) {
        boolean isValidEmail = emailValidatorService.test(adminRegistrationRequest.getEmail());

        if(isValidEmail) {
            String result = registerAdmin(
                    new Admin(
                            adminRegistrationRequest.getUsername(),
                            adminRegistrationRequest.getEmail(),
                            adminRegistrationRequest.getPassword()
                    ));

            return result.isEmpty() ? "User registered successfully!" : result;

        } else {
            //throw new IllegalStateException("please use your BISU email");
            return "please use your BISU email";
        }
    }

    public boolean isLogin(String email, String rawPassword) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        return admin.isPresent() && PasswordUtils.verifyPassword(rawPassword, admin.get().getPassword());
    }

    public Admin getAdminByEmail(String email) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if(admin.isPresent()) {
            return admin.get();
        } else {
            throw new UsernameNotFoundException(String.format(ADMIN_NOT_FOUND_MSG, email));
        }
    }

    public List<Admin> getAdminByAdminId(long adminId) {
        Optional<Admin> admin = adminRepository.findByAdminId(adminId);
        if(!admin.isPresent()) { return List.of(); }
        return List.of(admin.get());
    }
}
