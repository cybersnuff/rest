package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.DAO.UserDAO;
import ru.itmentor.spring.boot_security.demo.models.Role;
import ru.itmentor.spring.boot_security.demo.models.User;
import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class UserDataInit {
    private final UserDAO userDao;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserDataInit(UserDAO userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }
    // при включении, создатся 2 юзера, админ и юзер, с ролями админ и юзер
    @PostConstruct
    @Transactional
    public void init() {
        User admin = new User("admin", passwordEncoder.encode("admin"));
        User user = new User("user", passwordEncoder.encode("user"));
        admin.setRoles(Set.of(new Role("ROLE_ADMIN")));
        admin.setDepartment("IT");
        admin.setSalary(15000);
        user.setRoles(Set.of(new Role("ROLE_USER")));
        user.setDepartment("Sales");
        user.setSalary(10000);
        userDao.saveUser(admin, new String[]{"ROLE_ADMIN"});
        userDao.saveUser(user, new String[]{"ROLE_USER"});

    }
}
