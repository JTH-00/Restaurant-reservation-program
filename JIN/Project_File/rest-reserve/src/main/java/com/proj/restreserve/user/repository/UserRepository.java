package com.proj.restreserve.user.repository;

import com.proj.restreserve.restaurant.entity.Restaurant;
import com.proj.restreserve.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findByUseremail(String useremail);

    User findByUserid(String userid);
    Optional<User> findOneWithRolesByUseremail(String useremail);

    Page<User> findByBanTrue(Pageable pageable);
}