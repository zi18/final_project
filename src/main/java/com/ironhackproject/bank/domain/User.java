package com.ironhackproject.bank.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;

    @Column(name = "date_birth")
    private LocalDateTime dateBirth;

    @Column(name = "primary_address")
    private String primaryAddress;

    @Column(name = "mailing_address")
    private String mailingAddress;

    @Column(name = "hashed_key")
    private String hashedKey;

    @Id
    private String username;

    private String password;


    public User(String username) {
        this.username = username;
    }

}
