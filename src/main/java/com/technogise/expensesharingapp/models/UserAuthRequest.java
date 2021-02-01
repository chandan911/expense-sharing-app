package com.technogise.expensesharingapp.models;

import java.util.Objects;

public class UserAuthRequest {
  private String phoneNumber;
  private String password;

  public UserAuthRequest(String phoneNumber, String password) {
    this.phoneNumber = phoneNumber;
    this.password = password;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserAuthRequest)) return false;
    UserAuthRequest that = (UserAuthRequest) o;
    return phoneNumber.equals(that.phoneNumber) &&
        password.equals(that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(phoneNumber, password);
  }
}
