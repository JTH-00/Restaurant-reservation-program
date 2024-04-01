package com.proj.restreserve.board.repository;

import com.proj.restreserve.board.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository <Event,String> {
    List<Event> findByEventid(String eventid);

}
