package ru.itmentor.spring.boot_security.demo.configs.DAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.configs.models.Role;
import ru.itmentor.spring.boot_security.demo.configs.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }


    @Override
    @Transactional
    public void saveUser(User user, String[] role) {

        entityManager.persist(createUser(user, role));
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUser(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public void updateUser(User user, String[] role) {

        entityManager.merge(createUser(user, role));
    }


    private User createUser(User user, String[] role) {
        User newUser = new User();

        newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setDepartment(user.getDepartment());
        newUser.setSalary(user.getSalary());

        List<String> roles = Arrays.asList(role);

        getAllRoles().forEach(role1 -> {
            if (roles.contains(role1.getRoleName())) {
                newUser.getRoles().add(role1);
            }
        });

        return newUser;

    }


    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery(" FROM Role ", Role.class).getResultList();
    }

    @Override
    public User getUserByUsername(String username) {
        String hql = "FROM User WHERE username = :username";
        TypedQuery<User> typedQuery = entityManager.createQuery(hql, User.class);
        typedQuery.setParameter("username", username);
        List<User> userList = typedQuery.getResultList();

        if (userList.isEmpty())
            return null;

        return userList.get(0);
    }

    @Override
    public List<Role> getRoleByUsername(String username) {
        String hql = "SELECT roles FROM User " +
                "JOIN User.roles roles" +
                " WHERE User.username = :username";
        TypedQuery<Role> typedQuery = entityManager.createQuery(hql, Role.class);
        return typedQuery.setParameter("username", username).getResultList();
    }


}


