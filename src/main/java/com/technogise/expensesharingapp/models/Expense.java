package com.technogise.expensesharingapp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expense extends BasePersistenceModel {

  @Column(name = "desc", nullable = false)
  private String desc;

  @Column(name = "amount", nullable = false)
  private Double amount;

  @Column(name = "payer_id", nullable = false)
  private String payer_id;


  public Expense() {
  }

  public Expense(String desc, Double amount, String payer_id) {
    this.desc = desc;
    this.amount = amount;
    this.payer_id = payer_id;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getPayer_id() {
    return payer_id;
  }

  public void setPayer_id(String payer_id) {
    this.payer_id = payer_id;
  }

}
