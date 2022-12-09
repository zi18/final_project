package com.ironhackproject.bank.dto;

import com.ironhackproject.bank.domain.AccountType;
import com.ironhackproject.bank.domain.Status;
import com.ironhackproject.bank.exception.CreditLimitException;
import com.ironhackproject.bank.exception.InterestRateException;
import com.ironhackproject.bank.exception.MinimumBalanceException;
import com.ironhackproject.bank.service.AccountService;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountDTO {
    private Integer id;
    private BigDecimal balance;
    private String secretKey;
    private BigDecimal penaltyFee;
    private LocalDateTime creationDate;
    private Status status;
    private BigDecimal monthlyMaintenanceFee;
    private BigDecimal minBalance;
    private BigDecimal creditLimit;
    private BigDecimal interestRate;
    private AccountType accountType;
    private String primaryOwner;
    private String secondaryOwner;


    public AccountDTO(Integer id, BigDecimal balance, String secretKey, BigDecimal penaltyFee,
                      LocalDateTime creationDate, Status status, BigDecimal monthlyMaintenanceFee,
                      BigDecimal minBalance, BigDecimal creditLimit, BigDecimal interestRate,
                      AccountType accountType, String primaryOwner, String secondaryOwner) {
        this.id = id;
        this.balance = balance;
        this.secretKey = secretKey;
        this.penaltyFee = penaltyFee != null ? penaltyFee : new BigDecimal("40");
        this.creationDate = creationDate;
        this.status = status;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.minBalance = minBalance;
        this.creditLimit = creditLimit;
        this.accountType = accountType;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.interestRate = interestRate;
        validateAccount(minBalance, creditLimit, interestRate, accountType, monthlyMaintenanceFee);
    }

    private void validateAccount(BigDecimal minBalance, BigDecimal creditLimit,
                                 BigDecimal interestRate, AccountType accountType,
                                 BigDecimal monthlyMaintenanceFee) {
        if (accountType == AccountType.SAVINGS) {
            validateSavingsAccount(minBalance, interestRate);
        } else if (accountType == AccountType.CREDIT_CARD) {
            validateCreditCardAccount(creditLimit, interestRate);
        } else if (accountType == AccountType.CHECKING) {
            validateCheckingAccount(minBalance, monthlyMaintenanceFee);
        }
    }

    private void validateCheckingAccount(BigDecimal minBalance, BigDecimal monthlyMaintenanceFee) {
        BigDecimal defaultMinimumBalance = new BigDecimal("250");
        BigDecimal defaultMonthlyMaintenanceFee = new BigDecimal("12");

        if (minBalance == null) {
            this.minBalance = defaultMinimumBalance;
        }

        if (monthlyMaintenanceFee == null) {
            this.monthlyMaintenanceFee = defaultMonthlyMaintenanceFee;
        }
    }

    private void validateCreditCardAccount(BigDecimal creditLimit, BigDecimal interestRate) {
        BigDecimal defaultCreditLimit = new BigDecimal("100");
        BigDecimal maximumCreditLimit = new BigDecimal("100000");

        if (creditLimit == null) {
            this.creditLimit = defaultCreditLimit;
        } else {
            if (creditLimit.compareTo(new BigDecimal(0)) <= 0 || creditLimit.compareTo(maximumCreditLimit) > 0) {
                throw new CreditLimitException();
            }
        }

        // interest rate
        BigDecimal defaultInterestRate = new BigDecimal("0.2");
        BigDecimal maximumInterestRate = new BigDecimal("0.2");
        BigDecimal minimumInterestRate = new BigDecimal("0.1");

        if (interestRate == null) {
            this.interestRate = defaultInterestRate;
        } else {
            if (interestRate.compareTo(minimumInterestRate) < 0 || interestRate.compareTo(maximumInterestRate) > 0) {
                throw new InterestRateException();
            }
        }
    }

    private void validateSavingsAccount(BigDecimal minBalance, BigDecimal interestRate) {
        // interest rate
        BigDecimal defaultInterestRate = new BigDecimal("0.0025");
        BigDecimal maximumInterestRate = new BigDecimal("0.5");

        if (interestRate == null) {
            this.interestRate = defaultInterestRate;
        } else {
            if (interestRate.compareTo(new BigDecimal(0)) <= 0 || interestRate.compareTo(maximumInterestRate) > 0) {
                throw new InterestRateException();
            }
        }

        //minimum balance
        BigDecimal defaultMinimumBalance = new BigDecimal("1000");
        BigDecimal minimumMinimumBalance = new BigDecimal("100");

        if (minBalance == null) {
            this.minBalance = defaultMinimumBalance;
        } else {
            if (minBalance.compareTo(minimumMinimumBalance) < 0) {
                throw new MinimumBalanceException();
            }
        }
    }
}
