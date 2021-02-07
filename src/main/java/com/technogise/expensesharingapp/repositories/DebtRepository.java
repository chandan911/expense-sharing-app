package com.technogise.expensesharingapp.repositories;

import com.technogise.expensesharingapp.models.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Component("debtRepository")
public interface DebtRepository extends JpaRepository<Debt, Long> {

  @Query("SELECT d FROM Debt d WHERE d.creditorId=?1 or d.debtorId=?1 ORDER BY d.createdAt")
  List<Debt> getAllDebtsByUserId(Long userId);

  @Query("SELECT d FROM Debt d WHERE d.creditorId=?1 and d.debtorId=?2")
  Optional<Debt> getCreditorDebtorPair(Long payerId, Long debtorId);

  @Modifying
  @Query("UPDATE Debt d SET d.creditorId = ?2 , d.debtorId = ?3 , d.amount = ?4 WHERE d.id = ?1")
  int updateDebt(Long debtId, Long payerId, Long debtorId, Double amount);
}
