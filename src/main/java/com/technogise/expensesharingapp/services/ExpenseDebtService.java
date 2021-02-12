package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.ExpenseDebtor;
import org.springframework.stereotype.Component;

@Component("expenseDebtService")
public interface ExpenseDebtService {

    ExpenseDebtor createExpenseDebt(ExpenseDebtor expenseDebtor);
}
