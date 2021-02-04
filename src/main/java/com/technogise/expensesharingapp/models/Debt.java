package com.technogise.expensesharingapp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "debts")
public class Debt extends BasePersistenceModel {

  @Column(name = "creditor_id", nullable = false)
  private Long creditorId;

  @Column(name = "debtor_id", nullable = false)
  private Long debtorId;

  @Column(name = "amount", nullable = false)
  private Double amount;

  public Debt() {
  }

  public Debt(Long creditorId, Long debtorId, Double amount) {
    this.creditorId = creditorId;
    this.debtorId = debtorId;
    this.amount = amount;
  }

  public Long getCreditorId() {
    return creditorId;
  }

  public void setCreditorId(Long creditorId) {
    this.creditorId = creditorId;
  }

  public Long getDebtorId() {
    return debtorId;
  }

  public void setDebtorId(Long debtorId) {
    this.debtorId = debtorId;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }
}