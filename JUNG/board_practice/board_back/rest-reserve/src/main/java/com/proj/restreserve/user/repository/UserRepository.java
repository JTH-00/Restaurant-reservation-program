package com.proj.restreserve.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findByUseremail(String useremail);

    User findByUserid(String userid);
    Optional<User> findOneWithRolesByUseremail(String useremail);


}