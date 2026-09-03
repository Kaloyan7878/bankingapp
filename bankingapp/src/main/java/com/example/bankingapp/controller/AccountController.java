package com.example.bankingapp.controller;

import com.example.bankingapp.dto.TransactionRequest;
import com.example.bankingapp.dto.TransferRequest;
import com.example.bankingapp.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<String> deposit(@PathVariable String accountNumber, @RequestBody TransactionRequest request) {
        accountService.deposit(accountNumber, request.amount());
        return ResponseEntity.ok("Deposit successful");
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String accountNumber, @RequestBody TransactionRequest request) {
        accountService.withdraw(accountNumber, request.amount());
        return ResponseEntity.ok("Withdraw successful");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        accountService.transfer(request.fromAccountNumber(), request.toAccountNumber(), request.amount());
        return ResponseEntity.ok("Transfer successful");
    }
}
