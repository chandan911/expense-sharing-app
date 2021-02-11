package com.technogise.expensesharingapp.models;

import java.util.ArrayList;

public class NewExpenseRequest {

    private String description;
    private Double amount;
    private Long payerId;
    private ArrayList<Long> debtorIds;

    public NewExpenseRequest() {
    }

    public NewExpenseRequest(String description, Double amount, Long payerId, ArrayList<Long> debtorIds) {
        this.description = description;
        this.amount = amount;
        this.payerId = payerId;
        this.debtorIds = debtorIds;
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

    public ArrayList<Long> getDebtorIds() {
        return debtorIds;
    }

    public void setDebtorIds(ArrayList<Long> debtorIds) {
        this.debtorIds = debtorIds;
    }
}
