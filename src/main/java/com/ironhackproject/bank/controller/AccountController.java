package com.ironhackproject.bank.controller;

import com.ironhackproject.bank.dto.AccountBalanceDTO;
import com.ironhackproject.bank.dto.AccountDTO;
import com.ironhackproject.bank.dto.TransferDTO;
import com.ironhackproject.bank.mappings.AccountMapping;
import com.ironhackproject.bank.service.AccountService;
import com.ironhackproject.bank.service.Validations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {
    Logger logger =  LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountService accountService;

    @PostMapping
    public AccountDTO createAccount(
            Authentication authentication,
           @RequestBody AccountDTO accountDTO
    ) {
        logger.info(authentication.toString());
        Validations.validateAccount(accountDTO);
        return AccountMapping.accountEntityToAccountDTO(
                accountService.createAccount(
                        AccountMapping.accountDTOToAccountEntity(accountDTO),
                        authentication
                )
        );
    }

    @GetMapping
    public List<AccountDTO> getAccounts(Authentication authentication) {
        return AccountMapping.accountEntityListToAccountDTOList(
                accountService.getAccounts(authentication)
        );
    }

    @GetMapping("/{accountId}")
    public AccountDTO getAccount(
            @PathVariable(name = "accountId") Integer accountId,
            Authentication authentication
    ) {
        return AccountMapping.accountEntityToAccountDTO(
                accountService.getAccount(accountId, authentication)
        );
    }

    @PatchMapping("/{accountId}")
    public AccountDTO updateAccount(
            @PathVariable(name = "accountId") Integer accountId,
            @RequestBody AccountBalanceDTO accountBalanceDTO,
            Authentication authentication
            ) {
        return AccountMapping.accountEntityToAccountDTO(
                accountService.updateAccount(accountId, accountBalanceDTO.getBalance(), authentication)
        );
    }

    @PutMapping("/{accountId}/transfer")
    public AccountDTO transferMoney(
            @PathVariable(name = "accountId") Integer accountId,
            @RequestBody TransferDTO transferDTO,
            Authentication authentication
    ) {
        return AccountMapping.accountEntityToAccountDTO(
                accountService.transfer(accountId, transferDTO, authentication)
        );
    }

    @DeleteMapping("/{accountId}")
    public void deleteAccount(
            @PathVariable(name = "accountId") Integer accountId,
            Authentication authentication
    ) {
        accountService.deleteAccount(accountId, authentication);
    }
}
