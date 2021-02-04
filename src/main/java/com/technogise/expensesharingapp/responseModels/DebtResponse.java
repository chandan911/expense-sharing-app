package com.technogise.expensesharingapp.responseModels;

import java.io.Serializable;

public class DebtResponse implements Serializable {

  private Long id;

  private Double amount;

  private String creditor;

  private String debtor;

  public DebtResponse() {
  }

  public DebtResponse(Long id, Double amount, String creditor, String debtor) {
    this.id = id;
    this.amount = amount;
    this.creditor = creditor;
    this.debtor = debtor;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getCreditor() {
    return creditor;
  }

  public void setCreditor(String creditor) {
    this.creditor = creditor;
  }

  public String getDebtor() {
    return debtor;
  }

  public void setDebtor(String debtor) {
    this.debtor = debtor;
  }
}
