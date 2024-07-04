package com.spring.jwt.repository;

import com.spring.jwt.dto.BidCarDto;
import com.spring.jwt.entity.BidCarPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBidDoc extends JpaRepository<BidCarPhoto,Integer> {
    String addDocument(BidCarDto documentDto);

    Object getByUserId(Integer carId);

    Object getAllDocument(Integer carId, String documentType);

    Object getByCarID(Integer carId);

    Object getCarIdType(Integer carId, String docType);
}
