package com.example.patienttrackerapp.results;

public class ErrorResult extends Result {

    public ErrorResult(String message) {
        super(false, message);
    }
}
