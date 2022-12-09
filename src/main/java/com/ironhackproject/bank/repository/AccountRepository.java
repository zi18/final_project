package com.ironhackproject.bank.repository;

import com.ironhackproject.bank.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    List<Account> findByPrimaryOwnerUsername(String username);
    List<Account> findByIdAndPrimaryOwnerUsername(Integer id, String username);
}




