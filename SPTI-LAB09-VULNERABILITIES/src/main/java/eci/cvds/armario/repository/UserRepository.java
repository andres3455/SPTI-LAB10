package eci.cvds.armario.repository;

import eci.cvds.armario.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    //@Query("SELECT e FROM Users e WHERE e.username = ?1")
    User findByUsername(String username);
}