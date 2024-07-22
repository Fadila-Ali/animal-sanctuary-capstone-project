package com.ali.animalsanctuary.controller;

import com.ali.animalsanctuary.dto.UserDto;
import com.ali.animalsanctuary.entity.Animal;
import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthorizationController {

    private final UserService userService;

    @Autowired
    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null,  "This email is already associated with an account");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<UserDto> users = userService.getUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        UserDto userDto = userService.convertToDto(user);
        model.addAttribute("user", userDto);
        return "edit_user";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "edit_user";
        }
        userService.updateUser(userDto);
        return "redirect:/users?success";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users?deleted";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        UserDto userDto = userService.convertToDto(user);
        model.addAttribute("user", userDto);
        return "profile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "profile";
        }
        userService.updateUser(userDto);
        return "redirect:/profile?success";
    }

    @GetMapping("/profile/delete")
    public String deleteProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        userService.deleteUser(user.getId());
        return "redirect:/logout";
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public byte[] getAnimalImage(@PathVariable Long id) {
        User user = userService.findById(id);
        return user.getImage();
    }
}
