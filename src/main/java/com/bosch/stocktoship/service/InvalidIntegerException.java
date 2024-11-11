package com.bosch.stocktoship.service;
//CODE WRITTEN BY HARSHAVARDHAN VS - 6th November 2024

//Custom exception class to handle invalid integer inputs
public class InvalidIntegerException extends Exception {
 // Constructor that takes a message and passes it to the Exception superclass
 public InvalidIntegerException(String message) {
     super(message); // Call the superclass constructor with the provided message
 }
}
