package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller("/")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        userRepository.save(new User("ivan", "123", "ivan@mail.ru"));
        userRepository.save(new User("petr", "123", "petr222@mail.ru"));
        userRepository.save(new User("aleks", "123", "aleks12@mail.ru"));
    }

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user-list";
    }

    @GetMapping("/adduser")
    public String newUser(User user) {
        return "add-user";
    }

    @PostMapping("/adduser")
    public String newUser(User user, Model model) {
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "user-list";
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        // TODO доделать редактирование и убедиться что оно работает
        model.addAttribute("user", userRepository.findById(id));
        return "add-user";
    }

    @GetMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        // TODO доделать удаление пользователя
        model.addAttribute("users", userRepository.findAll());
        return "user-list";
    }
}
