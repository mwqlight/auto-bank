package com.bank.dto.response;

public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;
    private long timestamp;
    
    // 默认构造函数
    public ApiResponse() {
        this.timestamp = System.currentTimeMillis();
    }
    
    // 带参数的构造函数
    public ApiResponse(int code, String message, T data, long timestamp) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }
    
    // 便捷构造函数
    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
    
    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Getters and Setters
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public boolean isSuccess() {
        return code == 200;
    }
}