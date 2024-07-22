package com.innova.masraf_takip.DataAccessLayer;

import com.innova.masraf_takip.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<Object> findByName(String username);
}
