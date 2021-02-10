package com.technogise.expensesharingapp.responseModels;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpenseDebtResponse)) return false;
        ExpenseDebtResponse that = (ExpenseDebtResponse) o;
        return expenses.equals(that.expenses) && debts.equals(that.debts) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenses, debts);
    }
}
