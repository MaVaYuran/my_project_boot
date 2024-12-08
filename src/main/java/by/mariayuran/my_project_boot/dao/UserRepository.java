package by.mariayuran.my_project_boot.dao;

import by.mariayuran.my_project_boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findFirstByName(String name);

    Optional<User> findByName(String username);
}
