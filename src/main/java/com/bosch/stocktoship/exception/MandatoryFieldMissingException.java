package com.bosch.stocktoship.exception;
//CODE WRITTEN BY HARSHAVARDHAN VS - 6th November 2024

//Custom exception class to handle missing mandatory fields
public class MandatoryFieldMissingException extends Exception {
 // Constructor that takes a message and passes it to the Exception superclass
 public MandatoryFieldMissingException(String message) {
     super(message); // Call the superclass constructor with the provided message
 }
}
