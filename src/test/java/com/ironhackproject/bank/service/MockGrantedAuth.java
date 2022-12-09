package com.ironhackproject.bank.service;

import org.springframework.security.core.GrantedAuthority;

public class MockGrantedAuth implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "ADMIN";
    }
}
