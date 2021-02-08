package com.technogise.expensesharingapp.responseModels;

import com.technogise.expensesharingapp.models.User;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class AggregateDataResponse implements Serializable {
  private List<ExpenseResponse> expenses;

  private List<DebtResponse> debts;

  private User user;

  private List<User> otherUsers;

  public AggregateDataResponse() {
  }

  public AggregateDataResponse(List<ExpenseResponse> expenses, List<DebtResponse> debts, User user, List<User> otherUsers) {
    this.expenses = expenses;
    this.debts = debts;
    this.user = user;
    this.otherUsers = otherUsers;
  }

  public List<ExpenseResponse> getExpenses() {
    return expenses;
  }

  public void setExpenses(List<ExpenseResponse> expenses) {
    this.expenses = expenses;
  }

  public List<DebtResponse> getDebts() {
    return debts;
  }

  public void setDebts(List<DebtResponse> debts) {
    this.debts = debts;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<User> getOtherUsers() {
    return otherUsers;
  }

  public void setOtherUsers(List<User> otherUsers) {
    this.otherUsers = otherUsers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AggregateDataResponse)) return false;
    AggregateDataResponse that = (AggregateDataResponse) o;
    return expenses.equals(that.expenses) && debts.equals(that.debts) && user.equals(that.user) && otherUsers.equals(that.otherUsers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(expenses, debts, user, otherUsers);
  }
}
