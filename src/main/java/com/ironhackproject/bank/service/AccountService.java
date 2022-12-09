package com.ironhackproject.bank.service;

import com.ironhackproject.bank.domain.Account;
import com.ironhackproject.bank.domain.AccountType;
import com.ironhackproject.bank.domain.RoleType;
import com.ironhackproject.bank.domain.User;
import com.ironhackproject.bank.dto.TransferDTO;
import com.ironhackproject.bank.exception.*;
import com.ironhackproject.bank.repository.AccountRepository;
import com.ironhackproject.bank.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.ironhackproject.bank.Utils.calculateYears;

@Service
public class AccountService {

    Logger logger =  LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    InterestCalculatorService interestCalculatorService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    // get the age of the primaryOwner
    // validate if < 24 type is student else type checking
    public Account createAccount(Account account, Authentication authentication) {
        if (!userService.hasRole(authentication, RoleType.ADMIN)) {
            throw new ForbiddenException();
        }
        User user = userRepository.findByUsername(authentication.getName());
        validateCheckingAccount(account, user);
        return accountRepository.save(account);
    }

     @Transactional
    public Account getAccount(Integer accountId, Authentication authentication) {
         Account account;
         if (userService.hasRole(authentication, RoleType.ADMIN)) {
             account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
         } else {
             account = accountRepository.findByIdAndPrimaryOwnerUsername(
                     accountId,
                     authentication.getName()
             ).stream().findFirst().orElseThrow(AccountNotFoundException::new);
         }

        interestCalculatorService.calculateInterestRateForSavingsAccounts(account);
        interestCalculatorService.calculateInterestRateForCreditCards(account);

        return account;
    }

    private static void validateCheckingAccount(Account account, User user) {
        if (account.getAccountType() == AccountType.CHECKING) {
            int age = calculateYears(user.getDateBirth().toLocalDate(), LocalDate.now());
            if (age < 24) {
                account.setAccountType(AccountType.STUDENT_CHECKING);
            }
        }
    }

    public List<Account> getAccounts(Authentication authentication) {
        if(userService.hasRole(authentication, RoleType.ADMIN)) {
            return accountRepository.findAll();
        } else {
            return accountRepository.findByPrimaryOwnerUsername(authentication.getName());
        }
    }

    public Account updateAccount(Integer id, BigDecimal balance, Authentication authentication) {
        if (!userService.hasRole(authentication, RoleType.ADMIN)) {
            throw new ForbiddenException();
        }
        Account account =  accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        account.setBalance(balance);
        return accountRepository.save(account);
    }

    @Transactional
    public Account transfer(Integer accountId, TransferDTO transferDTO, Authentication authentication) {
        if (Objects.equals(accountId, transferDTO.getAccountId())) {
            throw new ForbiddenException();
        }
        Account fromAccount =  accountRepository.findByIdAndPrimaryOwnerUsername(accountId, authentication.getName())
                .stream().findFirst().orElseThrow(AccountNotFoundException::new);
        Account toAccount =  accountRepository.findById(transferDTO.getAccountId()).orElseThrow(AccountNotFoundException::new);
        BigDecimal currentBalance = fromAccount.getBalance();
        BigDecimal amountToTransfer = transferDTO.getAmount();
        if (amountToTransfer.compareTo(new BigDecimal("0")) <= 0) {
            throw new AmountException();
        }

        if (amountToTransfer.compareTo(currentBalance) <= 0) {
            // minus the amount form current account
            BigDecimal finalBalanceFrom = currentBalance.subtract(amountToTransfer);

            // penalty fee
            if (finalBalanceFrom.compareTo(fromAccount.getMinimumBalance()) < 0) {
                finalBalanceFrom = finalBalanceFrom.subtract(fromAccount.getPenaltyFee());
            }

            if (finalBalanceFrom.compareTo(new BigDecimal("0")) <= 0) {
                throw new NotEnoughFundsException();
            }

            fromAccount.setBalance(finalBalanceFrom);

            //500 + 100
            BigDecimal finalBalanceTo = toAccount.getBalance().add(amountToTransfer);
            toAccount.setBalance(finalBalanceTo);

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
        } else {
            //error
            throw new NotEnoughFundsException();
        }
        return fromAccount;
    }

    public void deleteAccount(Integer accountId, Authentication authentication) {
        if (!userService.hasRole(authentication, RoleType.ADMIN)) {
            throw new ForbiddenException();
        }
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
        accountRepository.delete(account);
    }
}
