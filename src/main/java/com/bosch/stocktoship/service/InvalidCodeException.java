package com.bosch.stocktoship.service;
//CODE WRITTEN BY HARSHAVARDHAN VS - 6th November 2024

//Custom exception class to handle invalid codes
public class InvalidCodeException extends Exception {
 // Constructor that takes a message and passes it to the Exception superclass
 public InvalidCodeException(String message) {
     super(message); // Call the superclass constructor with the provided message
 }
}
