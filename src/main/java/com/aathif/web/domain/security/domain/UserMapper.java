package com.aathif.web.domain.security.domain;

import com.aathif.web.domain.security.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserDetails mapToUserDetails(User user) {
        UserData userData = new UserData();
        userData.setUsername(user.getUsername());
        userData.setPassword(user.getPassword());

        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(String.valueOf(user.getUserRole()));

        userData.setAuthorities(List.of(grantedAuthority));
        return userData;
    }
}
