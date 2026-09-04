package com.example.bankingApp.service;

import com.example.bankingapp.entity.Account;
import com.example.bankingapp.repository.AccountRepository;
import com.example.bankingapp.service.AccountService;
import org.springframework.stereotype.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    private Account newAccount(String number, String balance) {
        Account a = new Account();
        a.setAccountNumber(number);
        a.setBalance(new BigDecimal(balance));
        return a;
    }

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setAccountNumber("BG22222");
        account.setBalance(new BigDecimal("100.00"));
    }

    @Test
    void depositIncreasesBalance() {
        when(accountRepository.findByAccountNumber("BG22222")).thenReturn(Optional.of(account));

        accountService.deposit("BG22222", new BigDecimal("50.00"));

        assertThat(account.getBalance()).isEqualByComparingTo("150.00");
        verify(accountRepository).save(account);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "-100.50"})
    void depositInvalidAmountThrows(String amount) {
        assertThrows(RuntimeException.class,
                () -> accountService.deposit("BG22222", new BigDecimal(amount)));
        verifyNoInteractions(accountRepository);
    }

    @Test
    void depositNullAmountThrows() {
        assertThrows(RuntimeException.class, () -> accountService.deposit("BG22222", null));
        verifyNoInteractions(accountRepository);
    }

    @Test
    void depositAccountNotFoundThrows() {
        when(accountRepository.findByAccountNumber("MISSING")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> accountService.deposit("MISSING", new BigDecimal("10")));
        verify(accountRepository, never()).save(any());
    }

    @Test
    void withdrawSufficientFundsDecreasesBalance() {
        when(accountRepository.findByAccountNumber("BG22222")).thenReturn(Optional.of(account));

        accountService.withdraw("BG22222", new BigDecimal("40.00"));

        assertThat(account.getBalance()).isEqualByComparingTo("60.00");
        verify(accountRepository).save(account);
    }

    @Test
    void withdrawInsufficientFundsThrows() {
        when(accountRepository.findByAccountNumber("BG22222")).thenReturn(Optional.of(account));

        assertThrows(RuntimeException.class,
                () -> accountService.withdraw("BG22222", new BigDecimal("200.00")));

        assertThat(account.getBalance()).isEqualByComparingTo("100.00");
        verify(accountRepository, never()).save(any());
    }

    @Test
    void withdrawInvalidAmountThrows() {
        assertThrows(RuntimeException.class,
                () -> accountService.withdraw("BG22222", BigDecimal.ZERO));
        verifyNoInteractions(accountRepository);
    }

    @Test
    void withdrawAccountNotFoundThrows() {
        when(accountRepository.findByAccountNumber("MISSING")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> accountService.withdraw("MISSING", new BigDecimal("10")));
    }

    @Test
    void transferFundsBetweenAccounts() {
        Account from = newAccount("BG22222", "100.00");
        Account to = newAccount("BG11111", "20.00");

        when(accountRepository.findByAccountNumber("BG22222")).thenReturn(Optional.of(from));
        when(accountRepository.findByAccountNumber("BG11111")).thenReturn(Optional.of(to));

        accountService.transfer("BG22222", "BG11111", new BigDecimal("30.00"));

        assertThat(from.getBalance()).isEqualByComparingTo("70.00");
        assertThat(to.getBalance()).isEqualByComparingTo("50.00");
    }

    @Test
    void transferInsufficientFundsThrows() {
        Account from = newAccount("BG22222", "10.00");
        Account to = newAccount("BG11111", "20.00");

        when(accountRepository.findByAccountNumber("BG22222")).thenReturn(Optional.of(from));
        when(accountRepository.findByAccountNumber("BG11111")).thenReturn(Optional.of(to));

        assertThrows(RuntimeException.class,
                () -> accountService.transfer("BG22222", "BG11111", new BigDecimal("50.00")));

        assertThat(from.getBalance()).isEqualByComparingTo("10.00");
        assertThat(to.getBalance()).isEqualByComparingTo("20.00");
    }
}
