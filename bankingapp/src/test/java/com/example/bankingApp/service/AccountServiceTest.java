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
}
