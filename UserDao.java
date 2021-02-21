package com.cursor.library.daos;

import com.cursor.library.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String userName);
}