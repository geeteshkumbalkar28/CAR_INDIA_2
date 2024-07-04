package com.spring.jwt.Interfaces;

import com.spring.jwt.dto.BrandData.BrandDataDto;
import com.spring.jwt.dto.BrandData.onlyBrandDto;

import java.util.List;

public interface BrandDataService {

    BrandDataDto addBrand(BrandDataDto brandDataDto);

    List<BrandDataDto> GetAllBrands();

    String editBrand(Integer brandDataId, BrandDataDto brandDataDto);

    public String deleteBrand(Integer brandDataId);

    List <onlyBrandDto> onlyBrands();

    List<BrandDataDto> variants(String brand);

    List<BrandDataDto> subVariant(String brand,String variant);

}
