package com.ironhackproject.bank.service;

import com.ironhackproject.bank.domain.Account;
import com.ironhackproject.bank.domain.AccountType;
import com.ironhackproject.bank.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.ironhackproject.bank.Utils.calculateMonths;
import static com.ironhackproject.bank.Utils.calculateYears;

@Service
public class InterestCalculatorService {

    @Autowired
    AccountRepository accountRepository;

    @Transactional
    public void calculateInterestRateForSavingsAccounts(Account account) {
        if (account.getAccountType() == AccountType.SAVINGS) {
            LocalDateTime creationDate = account.getCreationDate();
            LocalDateTime interestAddedDate = account.getInterestAddedDate();
            BigDecimal interestRate = account.getInterestRate();
            BigDecimal balance = account.getBalance();
            BigDecimal newBalance = balance.multiply(new BigDecimal("1").add(interestRate));
            if (interestAddedDate != null) {
                int numberOfYears = calculateYears(interestAddedDate.toLocalDate(), LocalDate.now());
                if (numberOfYears > 0) {
                    // apply interest
                    account.setBalance(newBalance);
                    account.setInterestAddedDate(LocalDateTime.now());
                    accountRepository.save(account);
                }
            } else {
                int numberOfYears = calculateYears(creationDate.toLocalDate(), LocalDate.now());
                if (numberOfYears > 0) {
                    // apply interest and update date
                    account.setBalance(newBalance);
                    account.setInterestAddedDate(LocalDateTime.now());
                    accountRepository.save(account);
                }
            }
        }
    }

    @Transactional
    public void calculateInterestRateForCreditCards(Account account) {
        if (account.getAccountType() == AccountType.CREDIT_CARD) {
            LocalDateTime creationDate = account.getCreationDate();
            LocalDateTime interestAddedDate = account.getInterestAddedDate();
            BigDecimal monthlyInterestRate = account.getInterestRate().divide(new BigDecimal("12"), RoundingMode.DOWN);
            BigDecimal balance = account.getBalance();
            BigDecimal newBalance = balance.multiply(new BigDecimal("1").add(monthlyInterestRate));

            if (interestAddedDate != null) {
                if (calculateMonths(interestAddedDate.toLocalDate(), LocalDate.now()) > 0) {
                    // apply interest
                    account.setBalance(newBalance);
                    account.setInterestAddedDate(LocalDateTime.now());
                    accountRepository.save(account);
                }
            } else {
                if (calculateMonths(creationDate.toLocalDate(), LocalDate.now()) > 0) {
                    // apply interest and update date
                    account.setBalance(newBalance);
                    account.setInterestAddedDate(LocalDateTime.now());
                    accountRepository.save(account);
                }
            }
        }
    }
}
