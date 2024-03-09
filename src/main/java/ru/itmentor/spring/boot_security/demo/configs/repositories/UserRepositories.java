package ru.itmentor.spring.boot_security.demo.configs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmentor.spring.boot_security.demo.configs.models.User;

public interface UserRepositories extends JpaRepository<User, Long> {

        User findByUsername(String username);

}
