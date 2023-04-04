package com.gamification.collectr.repository;

import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    Set<Role> findByUsers(MyUser user);
}