package org.xorrr.fundsoverview.retrieval;

@SuppressWarnings("serial")
public class InvalidIsinException extends Exception{

    public InvalidIsinException() {
        super();
    }

    public InvalidIsinException(String message) {
        super(message);
    }
}
