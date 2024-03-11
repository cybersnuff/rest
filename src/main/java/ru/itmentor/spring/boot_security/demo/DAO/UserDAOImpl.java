package ru.itmentor.spring.boot_security.demo.DAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.models.Role;
import ru.itmentor.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.*;


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


    public void saveRestUser(User user) {
        User usewr1 = new User();

        usewr1.setId(user.getId());
        usewr1.setUsername(user.getUsername());
        usewr1.setPassword(user.getPassword());
        usewr1.setDepartment(user.getDepartment());
        usewr1.setSalary(user.getSalary());

        getAllRoles().forEach(rolebd -> {

            System.out.println(user.getRoles());
           for (Role roleUser : user.getRoles()) {

               System.out.println(rolebd);
               System.out.println(roleUser);

             if  (rolebd.getRoleName().equals(roleUser.getRoleName())){
                 usewr1.getRoles().add(rolebd);

             };
           }
        });
        entityManager.persist(usewr1);
    }

    @Override
    public void updateRestUser(User user) {


        User usewr1 = new User();

        usewr1.setId(user.getId());
        usewr1.setUsername(user.getUsername());
        usewr1.setPassword(user.getPassword());
        usewr1.setDepartment(user.getDepartment());
        usewr1.setSalary(user.getSalary());

        getAllRoles().forEach(rolebd -> {

            System.out.println(user.getRoles());
            for (Role roleUser : user.getRoles()) {
                System.out.println(rolebd);
                System.out.println(roleUser);
                if  (rolebd.getRoleName().equals(roleUser.getRoleName())){
                    usewr1.getRoles().add(rolebd);

                };
            }
        });
        entityManager.merge(usewr1);
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


