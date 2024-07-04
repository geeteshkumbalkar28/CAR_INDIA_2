package com.spring.jwt.Interfaces;

import com.spring.jwt.dto.BidCarsDTO;
import com.spring.jwt.dto.BidDetailsDTO;

import java.util.List;
import java.util.UUID;

public interface BidCarsService {

    public BidCarsDTO createBidding(BidCarsDTO bidCarsDTO);

    public BidDetailsDTO getbyBidId (Integer bidCarId,  UUID beadingCarId);


    public List<BidCarsDTO> getByUserId(Integer userId);
}
