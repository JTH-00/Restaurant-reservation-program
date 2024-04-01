package com.proj.restreserve.board.repository;


import com.proj.restreserve.board.entity.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventImageRepository extends JpaRepository<EventImage,String> {
    List<EventImage> findByEvent_Eventid(String eventid);
}
