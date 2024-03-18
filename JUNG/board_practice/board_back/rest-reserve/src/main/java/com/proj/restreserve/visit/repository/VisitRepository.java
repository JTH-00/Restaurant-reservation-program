package com.proj.restreserve.visit.repository;

import com.proj.restreserve.user.entity.User;
import com.proj.restreserve.visit.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository <Visit,String> {
    List<Visit> findByUser(User user);
}
