package com.technogise.expensesharingapp.responseModels;

import java.io.Serializable;
import java.util.List;

public class ExpenseDebtResponse implements Serializable {

    private List<ExpenseResponse> expenses;

    private List<DebtResponse> debts;

    public ExpenseDebtResponse() {
    }

    public ExpenseDebtResponse(List<ExpenseResponse> expenses, List<DebtResponse> debts) {
        this.expenses = expenses;
        this.debts = debts;
    }

    public List<ExpenseResponse> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseResponse> expenses) {
        this.expenses = expenses;
    }

    public List<DebtResponse> getDebts() {
        return debts;
    }

    public void setDebts(List<DebtResponse> debts) {
        this.debts = debts;
    }
}
