package com.proj.restreserve.visit;

import com.proj.restreserve.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository <Visit,String> {
    List<Visit> findByUser(User user);
}
