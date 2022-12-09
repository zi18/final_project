package com.ironhackproject.bank.mappings;

import com.ironhackproject.bank.domain.Account;
import com.ironhackproject.bank.domain.User;
import com.ironhackproject.bank.dto.AccountDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AccountMapping {
    public static Account accountDTOToAccountEntity(AccountDTO accountDTO) {
        return new Account(
                null,
                accountDTO.getBalance(),
                accountDTO.getSecretKey(),
                new User(accountDTO.getPrimaryOwner()),
                accountDTO.getSecondaryOwner() != null ? new User(accountDTO.getSecondaryOwner()) : null,
                accountDTO.getMinBalance(),
                accountDTO.getPenaltyFee(),
                accountDTO.getMonthlyMaintenanceFee(),
                LocalDateTime.now(),
                accountDTO.getStatus(),
                accountDTO.getInterestRate(),
                accountDTO.getCreditLimit(),
                accountDTO.getAccountType(),
                null
        );
    }

    public static AccountDTO accountEntityToAccountDTO(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getBalance(),
                account.getSecretKey(),
                account.getPenaltyFee(),
                account.getCreationDate(),
                account.getStatus(),
                account.getMonthlyMaintenanceFee(),
                account.getMinimumBalance(),
                account.getCreditLimit(),
                account.getInterestRate(),
                account.getAccountType(),
                account.getPrimaryOwner().getUsername(),
                account.getSecondaryOwner() != null ? account.getSecondaryOwner().getUsername() : null
        );
    }

    public static List<AccountDTO> accountEntityListToAccountDTOList(List<Account> accounts) {
        return accounts.stream().map(AccountMapping::accountEntityToAccountDTO).collect(Collectors.toList());
    }
}
