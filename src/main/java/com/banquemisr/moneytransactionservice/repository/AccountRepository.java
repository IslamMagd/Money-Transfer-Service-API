package com.banquemisr.moneytransactionservice.repository;

import com.banquemisr.moneytransactionservice.model.Account;
import com.banquemisr.moneytransactionservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findAccountByUser(User user);

    Boolean existsByAccountNumber(String accountNumber);
    Boolean existsAccountByUser_EmailAndAccountNumber(String email, String accountNumber);
}
