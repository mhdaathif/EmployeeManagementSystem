package com.aathif.web.domain.security.repos;

import com.aathif.web.domain.security.model.User;
import com.aathif.web.domain.security.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    List<User> findAllByUserRole(UserRole userRole);

    Optional<User> findByMobile(String mobile);
}
