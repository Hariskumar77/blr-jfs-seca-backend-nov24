package com.bosch.stocktoship.exception;
//CODE WRITTEN BY HARSHAVARDHAN VS - 6th November 2024

//Custom exception class to handle invalid item codes
public class InvalidItemCodeException extends Exception {
 // Constructor that takes a message and passes it to the Exception superclass
 public InvalidItemCodeException(String message) {
     super(message); // Call the superclass constructor with the provided message
 }
}
