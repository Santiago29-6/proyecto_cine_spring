package com.cine.cine_remake.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;

import com.cine.cine_remake.model.Role;
import com.cine.cine_remake.model.Users;

public interface UserRepository extends JpaRepository<Users,Long>{

    Optional<Users> findByUsername(String username);

    List<Users> findUserByRole(Role role);

}