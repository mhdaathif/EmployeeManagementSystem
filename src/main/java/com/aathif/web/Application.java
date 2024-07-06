package com.aathif.web;

import com.aathif.web.domain.department.Department;
import com.aathif.web.domain.department.DepartmentRepository;
import com.aathif.web.domain.job.JobRole;
import com.aathif.web.domain.job.JobRoleRepository;
import com.aathif.web.domain.security.model.User;
import com.aathif.web.domain.security.model.UserRole;
import com.aathif.web.domain.security.repos.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class Application {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;
    private final JobRoleRepository jobRoleRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init() {

        Department department = departmentRepository.findByName("Admin").orElseGet(() -> departmentRepository.save(
                Department.builder()
                        .name("Admin")
                        .build()
        ));


        JobRole jobRole = jobRoleRepository.findByTitle("Admin").orElseGet(() -> jobRoleRepository.save(
                JobRole.builder()
                        .title("Admin")
                        .salary(0.0)
                        .departmentId(department.getId())
                        .build()
        ));

        if (userRepository.findByUsername("ahbrand0714@gmail.com").isEmpty()) {
            userRepository.save(
                    User.builder()
                            .name("Aathif")
                            .username("ahbrand0714@gmail.com")
                            .mobile("0779782443")
                            .password(passwordEncoder.encode("1234"))
                            .status(true)
                            .delete(false)
                            .userRole(UserRole.ADMIN)
                            .jobRoleId(jobRole.getId())
                            .build()
            );
        }

        if (userRepository.findByUsername("aathif.jiat@gmail.com").isEmpty()) {
            userRepository.save(
                    User.builder()
                            .name("Aathif")
                            .username("aathif.jiat@gmail.com")
                            .mobile("0779782443")
                            .password(passwordEncoder.encode("1234"))
                            .status(true)
                            .delete(false)
                            .userRole(UserRole.USER)
                            .jobRoleId(jobRole.getId())
                            .build()
            );
        }

    }

}
