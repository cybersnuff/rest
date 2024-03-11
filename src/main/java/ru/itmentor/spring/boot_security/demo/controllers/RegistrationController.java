package ru.itmentor.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmentor.spring.boot_security.demo.models.User;
import ru.itmentor.spring.boot_security.demo.repositories.UserRepositories;
import ru.itmentor.spring.boot_security.demo.service.UserService;


@Controller
public class RegistrationController {

    @Autowired
    private UserRepositories userRepositories;
    @Autowired
    private UserService userService;
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") User user, Model model) {

        if (userRepositories.findByUsername(user.getUsername()) != null) {
            model.addAttribute("usernameExists", true);
            return "registration";
        }
        String[] mass = {"ROLE_USER", "ROLE_ADMIN"}; // новый пользователь будет сохранен, с 2-мя ролями, для отладки
        userService.saveUser(user, mass);
        return "redirect:/login";
    }
}
