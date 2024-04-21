package com.proj.restreserve.alarm.repository;

import com.proj.restreserve.alarm.entity.Alarm;
import com.proj.restreserve.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, String> {
    Page<Alarm> findByUser(User user, Pageable pageable);
}
