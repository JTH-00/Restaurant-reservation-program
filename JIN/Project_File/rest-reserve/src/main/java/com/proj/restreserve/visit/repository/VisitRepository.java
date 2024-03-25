package com.proj.restreserve.visit.repository;

import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.visit.dto.VisitDto;
import com.proj.restreserve.visit.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository <Visit,String> {
    Boolean existsByUserAndVisittime(User user, LocalDateTime visittime);
}
