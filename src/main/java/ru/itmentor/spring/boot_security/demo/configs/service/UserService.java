package ru.itmentor.spring.boot_security.demo.configs.service;

import ru.itmentor.spring.boot_security.demo.configs.models.Role;
import ru.itmentor.spring.boot_security.demo.configs.models.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    public List<User> getAllUsers();
    public void saveUser(User user, String[] role);
    public User getUser(Long id);
    public void deleteUser(Long id);
    public void updateUser(User user, String[] role);

    List<Role> getAllRoles();

    User getUserByUsername(String username);

    List<Role> getRoleByUsername(String username);
}
