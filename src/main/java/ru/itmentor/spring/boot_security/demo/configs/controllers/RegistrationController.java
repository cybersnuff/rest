package ru.itmentor.spring.boot_security.demo.configs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmentor.spring.boot_security.demo.configs.models.Role;
import ru.itmentor.spring.boot_security.demo.configs.models.User;
import ru.itmentor.spring.boot_security.demo.configs.repositories.UserRepositories;
import ru.itmentor.spring.boot_security.demo.configs.service.UserService;


import java.util.Collections;
import java.util.Map;


@Controller
public class RegistrationController {

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private UserService userService;


//    @GetMapping("/registration")
//    public String RegistrationForm() {
//        return "registration";
//
//    }


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
// Логика сохранения нового пользователя
        userService.saveUser(user);
        return "redirect:/login"; // Перенаправление на страницу входа

    }
}
