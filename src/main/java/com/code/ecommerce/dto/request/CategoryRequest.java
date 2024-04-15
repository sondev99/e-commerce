package com.code.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryRequest {

    private String name;
    private String parentCatId;
    private MultipartFile imageFile;
    private MultipartFile iconFile;

}