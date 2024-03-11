package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.models.Role;
import ru.itmentor.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public void saveUser(User user, String[] role);
    public User getUser(Long id);
    public void deleteUser(Long id);
    public void updateUser(User user, String[] role);
    List<Role> getAllRoles();
    User getUserByUsername(String username);
    List<Role> getRoleByUsername(String username);
    public void saveRestUser(User user);
    public void updateRestUser(User user);
}
