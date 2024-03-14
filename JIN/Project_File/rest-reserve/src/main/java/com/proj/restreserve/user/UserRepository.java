package com.proj.restreserve.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findByUseremail(String useremail);

    Optional<User> findOneWithRolesByUseremail(String useremail);

}