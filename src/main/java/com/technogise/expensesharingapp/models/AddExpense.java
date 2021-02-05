package com.technogise.expensesharingapp.models;

import java.util.ArrayList;

public class AddExpense {

    private String description;
    private Double amount;
    private Long payerId;
    private ArrayList<Long> debtorId;

    public AddExpense(String description, Double amount, Long payerId, ArrayList<Long> debtorId) {
        this.description = description;
        this.amount = amount;
        this.payerId = payerId;
        this.debtorId = debtorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getPayerId() {
        return payerId;
    }

    public void setPayerId(Long payerId) {
        this.payerId = payerId;
    }

    public ArrayList<Long> getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(ArrayList<Long> debtorId) {
        this.debtorId = debtorId;
    }
}
