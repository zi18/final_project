package com.ironhackproject.bank.service;

import com.ironhackproject.bank.domain.AccountType;
import com.ironhackproject.bank.dto.AccountDTO;
import com.ironhackproject.bank.exception.AccountTypeException;
import com.ironhackproject.bank.exception.MandatoryFieldsException;

public class Validations {
    public static void validateAccount(AccountDTO accountDTO) {
        if (accountDTO.getBalance() == null) throw new MandatoryFieldsException();
        if (accountDTO.getPenaltyFee() == null) throw new MandatoryFieldsException();
        if (accountDTO.getPrimaryOwner() == null) throw new MandatoryFieldsException();

        if (accountDTO.getAccountType() == AccountType.CHECKING) {
            if (accountDTO.getSecretKey() == null) throw new MandatoryFieldsException();
            if (accountDTO.getMinBalance() == null) throw new MandatoryFieldsException();
            if (accountDTO.getMonthlyMaintenanceFee() == null) throw new MandatoryFieldsException();
            if (accountDTO.getStatus() == null) throw new MandatoryFieldsException();
        } else if (accountDTO.getAccountType() == AccountType.STUDENT_CHECKING) {
            if (accountDTO.getSecretKey() == null) throw new MandatoryFieldsException();
            if (accountDTO.getStatus() == null) throw new MandatoryFieldsException();
        } else if (accountDTO.getAccountType() == AccountType.SAVINGS) {
            if (accountDTO.getSecretKey() == null) throw new MandatoryFieldsException();
            if (accountDTO.getMinBalance() == null) throw new MandatoryFieldsException();
            if (accountDTO.getStatus() == null) throw new MandatoryFieldsException();
            if (accountDTO.getInterestRate() == null) throw new MandatoryFieldsException();
        } else if (accountDTO.getAccountType() == AccountType.CREDIT_CARD) {
            if (accountDTO.getCreditLimit() == null) throw new MandatoryFieldsException();
            if (accountDTO.getInterestRate() == null) throw new MandatoryFieldsException();
        } else {
            throw new AccountTypeException();
        }
    }
}
