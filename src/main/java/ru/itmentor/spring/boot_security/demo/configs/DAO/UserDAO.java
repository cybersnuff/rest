package ru.itmentor.spring.boot_security.demo.configs.DAO;

import ru.itmentor.spring.boot_security.demo.configs.models.Role;
import ru.itmentor.spring.boot_security.demo.configs.models.User;

import java.util.List;

public interface UserDAO {
    public User getUser(Long id);
    public List<User> getAllUsers();
    public void saveUser(User user);
    public void deleteUser(Long id);
    public void updateUser(User user);

    List<Role> getAllRoles();

    User getUserByUsername(String username);

    List<Role> getRoleByUsername(String username);
}
