package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;


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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", userRepository.findById(id));
        return "edit-user";
    }

//    @PostMapping("/edit/{id}")
    @PostMapping("/user/{id}/edit")
    public String editUser(@PathVariable("id") Long id,
                           @Valid User user,
                           BindingResult result,
                           Model model) {
//        System.out.println(user.getUsername());
        if (result.hasErrors()) {
            user.setId(id);
//            System.out.println(user.getUsername());
            return "edit-user";
        }

//        System.out.println(user.getUsername());
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "user-list";
    }

    @GetMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "user-list";
    }
}
