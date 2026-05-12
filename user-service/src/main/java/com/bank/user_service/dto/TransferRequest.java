package com.bank.user_service.dto;


import jakarta.validation.constraints.*;

public class TransferRequest {
    @NotNull
    private String fromEmail;
    @NotNull
    private String toEmail;
    @NotNull
    @Min(1)
    private Double amount;

    // getters & setters


    public @NotNull String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(@NotNull String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public @NotNull String getToEmail() {
        return toEmail;
    }

    public void setToEmail(@NotNull String toEmail) {
        this.toEmail = toEmail;
    }

    public @NotNull @Min(1) Double getAmount() {
        return amount;
    }

    public void setAmount(@NotNull @Min(1) Double amount) {
        this.amount = amount;
    }
}