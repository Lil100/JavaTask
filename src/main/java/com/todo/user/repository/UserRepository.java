package com.todo.user.repository;


import com.todo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<com.todo.user.entity.User> findByUsername(String username);
}
