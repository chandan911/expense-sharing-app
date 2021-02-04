package com.technogise.expensesharingapp.repositories;

import com.technogise.expensesharingapp.models.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component("debtRepository")
public interface DebtRepository extends JpaRepository<Debt, Long> {

  @Query("SELECT d FROM Debt d WHERE d.creditorId=?1 or d.debtorId=?1 ORDER BY d.createdAt")
  List<Debt> getAllDebtsByUserId(Long userId);

}
