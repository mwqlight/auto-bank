package com.bank.util;

import com.bank.dto.response.ApiResponse;

public class ResponseUtil {
    
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(200, "操作成功", data, System.currentTimeMillis());
    }
    
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<T>(200, message, data, System.currentTimeMillis());
    }
    
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<T>(code, message, null, System.currentTimeMillis());
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<T>(500, message, null, System.currentTimeMillis());
    }
}