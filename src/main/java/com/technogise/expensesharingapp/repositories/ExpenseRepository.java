package com.technogise.expensesharingapp.repositories;

import com.technogise.expensesharingapp.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component("expenseRepository")
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  @Query("SELECT e FROM Expense e WHERE e.payerId=?1 or " +
      "e.id in (SELECT d.expenseId FROM ExpenseDebtor d WHERE d.debtorId=?1) " +
      "ORDER BY e.createdAt")
  List<Expense> getAllExpensesById (Long userId);

}
