package com.proj.restreserve.board.repository;

import com.proj.restreserve.board.entity.NoticeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeImageRepository extends JpaRepository<NoticeImage,String> {
    List<NoticeImage> findByNotice_Noticeid(String noticeid);
    void deleteByImagelink(String imagelink);
}
