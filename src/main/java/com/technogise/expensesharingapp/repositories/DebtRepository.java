package com.technogise.expensesharingapp.repositories;

import com.technogise.expensesharingapp.models.Debt;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Component("debtRepository")
public interface DebtRepository extends JpaRepository<Debt, Long> {

  @Query("SELECT d FROM Debt d WHERE d.creditorId=?1 or d.debtorId=?1 ORDER BY d.updatedAt DESC")
  List<Debt> findAllDebtsByUserId(Long userId);

  @Query("SELECT d FROM Debt d WHERE d.creditorId=?1 and d.debtorId=?2")
  Optional<Debt> getCreditorDebtorPair(Long payerId, Long debtorId);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("UPDATE Debt d SET d.creditorId = ?2 , d.debtorId = ?3 , d.amount = ?4, d.updatedAt = ?5 WHERE d.id = ?1")
  Integer updateDebt(Long debtId, Long payerId, Long debtorId, Double amount,Date u);
}
