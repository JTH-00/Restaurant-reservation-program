package com.proj.restreserve.user.repository;

import com.proj.restreserve.user.entity.BusinessNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessNumberRepository extends JpaRepository <BusinessNumber,String> {
}
