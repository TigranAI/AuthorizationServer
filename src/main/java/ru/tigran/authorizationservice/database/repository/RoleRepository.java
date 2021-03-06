package ru.tigran.authorizationservice.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tigran.authorizationservice.database.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query("select r from Role r where r.name = ?1")
    Role findByName(String name);
}
