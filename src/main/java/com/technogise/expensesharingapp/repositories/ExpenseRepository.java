package com.technogise.expensesharingapp.repositories;

import com.technogise.expensesharingapp.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component("expenseRepository")
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
