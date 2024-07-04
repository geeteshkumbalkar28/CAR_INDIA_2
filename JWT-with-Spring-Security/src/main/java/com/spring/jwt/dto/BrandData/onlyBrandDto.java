package com.spring.jwt.dto.BrandData;

import lombok.Data;

@Data
public class onlyBrandDto {

    private String brand;

    public onlyBrandDto(String brand) {
        this.brand = brand;
    }
}

