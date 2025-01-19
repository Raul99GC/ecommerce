package com.raulcg.ecommerce.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.raulcg.ecommerce.exceptions.ImageUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
public class CloudinaryUtils {

    @Value("${cloudinary.api.key}")
    private String CLOUDINARY_API_KEY;

    @Value("${cloudinary.api.secret}")
    private String CLOUDINARY_API_SECRET;

    @Value("${cloudinary.api.cloud.name}")
    private String CLOUDINARY_API_CLOUD_NAME;

    public String uploadImage(String fileName, MultipartFile file) {

        String cloudinaryUrl = "cloudinary://" + CLOUDINARY_API_KEY + ":" + CLOUDINARY_API_SECRET + "@" + CLOUDINARY_API_CLOUD_NAME;

        try {
            Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);

            Map<String, Object> params = ObjectUtils.asMap(
                    "fileName", true,
                    "unique_filename", false,
                    "overwrite", true
            );

            // Subir el archivo a Cloudinary
            Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), params);

            return (String) result.get("secure_url");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new ImageUploadException("Error uploading image");
        }
    }

}
