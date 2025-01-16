package com.jonathas.drinkeasy.exceptions;

public class SalesOrderNotFoundException extends RuntimeException {
  public SalesOrderNotFoundException(String message) {
    super(message);
  }
}
