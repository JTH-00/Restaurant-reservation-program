package com.proj.restreserve.menu.repository;

import com.proj.restreserve.menu.entity.MenuImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuImageRepository extends JpaRepository<MenuImage,String>{
}
