package com.ironhackproject.bank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhackproject.bank.controller.AccountController;
import com.ironhackproject.bank.domain.Account;
import com.ironhackproject.bank.domain.AccountType;
import com.ironhackproject.bank.domain.Status;
import com.ironhackproject.bank.domain.User;
import com.ironhackproject.bank.dto.AccountDTO;
import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.security.core.Authentication;

@SpringBootTest
public class AccountsServiceTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private Authentication auth;

    @Test
    void testCreateAccount() {
        Mockito.when(auth.getName()).thenReturn("zineb");
        Collection authorities = Collections.singletonList(new MockGrantedAuth());
        Mockito.when(auth.getAuthorities()).thenReturn(authorities);
        assertThat(accountService).isNotNull();
        accountService.createAccount(
                new Account(
                        null,
                        new BigDecimal("1000"),
                        "secret",
                        new User("zineb"),
                        null,
                        new BigDecimal("100"),
                        new BigDecimal("40"),
                        new BigDecimal("2"),
                        LocalDateTime.now(),
                        Status.ACTIVE,
                        new BigDecimal("0.01"),
                        new BigDecimal("1000"),
                        AccountType.CHECKING,
                        null
                ),
                auth
        );
    }


}
