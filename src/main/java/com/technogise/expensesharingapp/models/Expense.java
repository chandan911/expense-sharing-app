package com.technogise.expensesharingapp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expense extends BasePersistenceModel {

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "amount", nullable = false)
  private Double amount;

  @Column(name = "payer_id", nullable = false)
  private Long payerId;

  public Expense() {
  }

  public Expense(String description, Double amount, Long payerId) {
    this.description = description;
    this.amount = amount;
    this.payerId = payerId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Long getPayerId() {
    return payerId;
  }

  public void setPayerId(Long payerId) {
    this.payerId = payerId;
  }
}
