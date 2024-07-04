package com.spring.jwt.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class BidCarDto {

    private String documentType;

    private String documentLink;


    private String doc;
    private String doctype;
    private String subtype;
    private String comment;

}