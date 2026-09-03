package com.example.bankingapp.dto;

import java.math.BigDecimal;

public record TransactionRequest(BigDecimal amount) {
}