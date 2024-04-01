package com.proj.restreserve.menucategory.repository;

import com.proj.restreserve.menucategory.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory,String> {
}
