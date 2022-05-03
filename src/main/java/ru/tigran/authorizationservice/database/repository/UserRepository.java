package ru.tigran.authorizationservice.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tigran.authorizationservice.database.entity.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);

    User findByRefreshToken(String refreshToken);

    Boolean existsByUsername(String username);
}
