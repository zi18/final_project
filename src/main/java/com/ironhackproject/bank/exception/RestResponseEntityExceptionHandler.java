package com.ironhackproject.bank.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { AccountNotFoundException.class })
    protected ResponseEntity<Object> handleAccountNotFoundException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Account was not found";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value
            = { AccountTypeException.class })
    protected ResponseEntity<Object> handleAccountTypeException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Account type is incorrect";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = { AmountException.class })
    protected ResponseEntity<Object> handleAmountException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "The amount is incorrect";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = { CreditLimitException.class })
    protected ResponseEntity<Object> handleCreditLimitException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "The credit limit amount is incorrect";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = { ForbiddenException.class })
    protected ResponseEntity<Object> handleForbiddenException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Access forbidden";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value
            = { InterestRateException.class })
    protected ResponseEntity<Object> handleInterestRateException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Interest rate value is incorrect";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = { MandatoryFieldsException.class })
    protected ResponseEntity<Object> handleMandatoryFieldsException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This field is mandatory";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value
            = { MinimumBalanceException.class })
    protected ResponseEntity<Object> handleMinimumBalanceException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "The minimum balance was reached ";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_GATEWAY, request);
    }

    @ExceptionHandler(value
            = { NotEnoughFundsException.class })
    protected ResponseEntity<Object> handleNotEnoughFundsException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "The funds are insufficient ";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_GATEWAY, request);
    }

    @ExceptionHandler(value
            = { UserNotFoundException.class })
    protected ResponseEntity<Object> handleUserNotFoundException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "The user doesn't exists";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}