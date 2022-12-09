package com.ironhackproject.bank.service;


import com.ironhackproject.bank.domain.RoleType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public boolean hasRole(Authentication authentication, RoleType rol) {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(rol.name()));
    }
}
