package com.raulcg.ecommerce.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageUploadResponse {
    private boolean success;
    private String url;

    public ImageUploadResponse(boolean success, String url) {
        this.success = success;
        this.url = url;
    }
}
