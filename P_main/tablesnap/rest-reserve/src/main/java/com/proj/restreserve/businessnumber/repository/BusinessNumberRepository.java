package com.proj.restreserve.businessnumber.repository;

import com.proj.restreserve.businessnumber.entity.BusinessNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessNumberRepository extends JpaRepository <BusinessNumber,String> {
}
