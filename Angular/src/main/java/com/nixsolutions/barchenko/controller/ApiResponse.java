package com.nixsolutions.barchenko.controller;

public class ApiResponse<T> {

    private int status;

    private Object result;

    private String message;

    public ApiResponse() {
    }

    public ApiResponse(int status, Object result, String message) {
        this.status = status;
        this.result = result;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
