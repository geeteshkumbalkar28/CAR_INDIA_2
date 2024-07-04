package com.spring.jwt.repository;

import com.spring.jwt.entity.BeadingCAR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface BeadingCarRepo extends JpaRepository<BeadingCAR, UUID>, JpaSpecificationExecutor<BeadingCAR> {

    List<BeadingCAR> findByUserId (int userId);


}
