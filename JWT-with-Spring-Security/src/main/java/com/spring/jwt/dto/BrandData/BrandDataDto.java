package com.spring.jwt.dto.BrandData;

import lombok.Data;

@Data
public class BrandDataDto {

    private int brandDataId;

    private String brand;

    private String variant;

    private String subVariant;
}
