package com.technogise.expensesharingapp.repositories;

import com.technogise.expensesharingapp.models.ExpenseDebtor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component("expenseDebtorRepository")
public interface ExpenseDebtorRepository extends JpaRepository<ExpenseDebtor, Long> {
}
