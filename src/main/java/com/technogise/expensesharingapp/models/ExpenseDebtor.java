package com.technogise.expensesharingapp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "expense_debtors")
public class ExpenseDebtor extends BasePersistenceModel {

  @Column(name = "expense_id", nullable = false)
  private Long expenseId;

  @Column(name = "debtor_id", nullable = false)
  private Long debtorId;

  public ExpenseDebtor() {
  }

  public ExpenseDebtor(Long expenseId, Long debtorId) {
    this.expenseId = expenseId;
    this.debtorId = debtorId;
  }

  public Long getExpenseId() {
    return expenseId;
  }

  public void setExpenseId(Long expenseId) {
    this.expenseId = expenseId;
  }

  public Long getDebtorId() {
    return debtorId;
  }

  public void setDebtorId(Long debtorId) {
    this.debtorId = debtorId;
  }
}
