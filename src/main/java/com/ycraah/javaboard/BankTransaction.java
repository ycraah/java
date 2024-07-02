package com.ycraah.javaboard;

import java.time.LocalDate;

public class BankTransaction {
  private final LocalDate date;
  private final double amount;
  private final String description;

  public BankTransaction(LocalDate date, double amount, String description) {
    this.date = date;
    this.amount = amount;
    this.description = description;
  }

  public LocalDate getDate() {
    return date;
  }

  public double getAmount() {
    return amount;
  }

  public String getDescription() {
    return description;
  }
}
