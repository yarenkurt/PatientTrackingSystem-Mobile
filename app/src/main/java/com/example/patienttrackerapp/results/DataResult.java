package com.example.patienttrackerapp.results;

public class DataResult<T>  extends com.example.patienttrackerapp.results.Result {
    public T Data;

    public DataResult(T data,boolean success, String message) {
        super(success, message);
        Data=data;
    }
}
