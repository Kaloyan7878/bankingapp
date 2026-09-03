package com.example.bankingapp.config;

import com.example.bankingapp.entity.Account;
import com.example.bankingapp.entity.User;
import com.example.bankingapp.repository.AccountRepository;
import com.example.bankingapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner{
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public DataLoader(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) {
        User pencho = new User();
        pencho.setFirstName("Pencho");
        pencho.setLastName("Penchev");
        userRepository.save(pencho);

        Account acc1 = new Account();
        acc1.setAccountNumber("BG12BANK1111111111");
        acc1.setBalance(new BigDecimal("1000.00"));
        acc1.setUser(pencho);
        accountRepository.save(acc1);

        User penka = new User();
        penka.setFirstName("Penka");
        penka.setLastName("Penkova");
        userRepository.save(penka);

        Account acc2 = new Account();
        acc2.setAccountNumber("BG12BANK2222222222");
        acc2.setBalance(new BigDecimal("500.00"));
        acc2.setUser(penka);
        accountRepository.save(acc2);

        System.out.println("Demo data loaded: BG111 (1000 BGN), BG222 (500 BGN)");
    }
}
