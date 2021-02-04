package com.technogise.expensesharingapp.responseModels;

import java.io.Serializable;
import java.util.Date;

public class ExpenseResponse implements Serializable {

  private String description;
  private Double amount;
  private String payerName;
  private Date dateTime;

  public ExpenseResponse() {
  }

  public ExpenseResponse(String description, Double amount, String payerName, Date dateTime) {
    this.description = description;
    this.amount = amount;
    this.payerName = payerName;
    this.dateTime = dateTime;
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

  public String getPayerName() {
    return payerName;
  }

  public void setPayerName(String payerName) {
    this.payerName = payerName;
  }

  public Date getDateTime() {
    return dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

}
