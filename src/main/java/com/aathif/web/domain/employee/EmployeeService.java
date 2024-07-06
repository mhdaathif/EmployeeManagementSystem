package com.aathif.web.domain.employee;

import com.aathif.web.domain.security.model.User;
import com.aathif.web.domain.security.model.UserRole;
import com.aathif.web.domain.security.repos.UserRepository;
import com.aathif.web.dto.ApplicationResponseDTO;
import com.aathif.web.exception.ApplicationCustomException;
import com.aathif.web.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public ApplicationResponseDTO createUser(EmployeeDTO employeeDTO) {
        if (userRepository.findByUsername(employeeDTO.getUsername()).isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "USERNAME_ALREADY_EXIST", "Username Already Exist");
        } else if (userRepository.findByMobile(employeeDTO.getMobile()).isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "MOBILE_ALREADY_EXIST", "Mobile Already Exist");
        } else {
            String generatePassword = generatePassword();
            mailService.sendAccountCredentialMail("User Account Credentials", employeeDTO.getUsername(), employeeDTO.getName(), generatePassword);
            userRepository.save(
                    User.builder()
                            .name(employeeDTO.getName())
                            .username(employeeDTO.getUsername())
                            .mobile(employeeDTO.getMobile())
                            .password(passwordEncoder.encode(generatePassword))
                            .status(true)
                            .delete(false)
                            .userRole(UserRole.USER)
                            .build()
            );
            return new ApplicationResponseDTO(HttpStatus.CREATED, "USER_REGISTERED_SUCCESSFULLY", "User Registered Successfully!");
        }
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAllByUserRole(UserRole.USER);
        if (users.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "NO_USERS_FOUND", "No Users Found");
        }
        return users;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "User Not Found"));
    }

    public ApplicationResponseDTO changeUserStatus(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "User Not Found"));
        user.setStatus(!user.isStatus());
        userRepository.save(user);
        return new ApplicationResponseDTO(HttpStatus.OK, "USER_STATUS_CHANGED_SUCCESSFULLY", "User Status Changes Successfully!");
    }

    public ApplicationResponseDTO deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ApplicationCustomException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "User Not Found"));
        user.setDelete(true);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        return new ApplicationResponseDTO(HttpStatus.OK, "USER_DELETED_SUCCESSFULLY", "User Deleted Successfully!");
    }

    private String generatePassword() {
        byte[] randomBytes = new byte[12];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

}
