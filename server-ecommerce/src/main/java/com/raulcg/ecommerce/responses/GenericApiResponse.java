package com.raulcg.ecommerce.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public GenericApiResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
    public GenericApiResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }
}