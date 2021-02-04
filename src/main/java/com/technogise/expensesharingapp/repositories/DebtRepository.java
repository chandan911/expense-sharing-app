package com.technogise.expensesharingapp.repositories;

import com.technogise.expensesharingapp.models.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component("debtRepository")
public interface DebtRepository extends JpaRepository<Debt, Long> {
}
