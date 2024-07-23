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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Controller for handling user authorization and profile management operations.
 *
 * <p>This class provides endpoints for user registration, login, profile management,
 * and user listing. It also includes functionality for editing and deleting user profiles.
 *
 * <p>Endpoints:
 * <ul>
 *     <li>{@code /index}: Displays the home page.</li>
 *     <li>{@code /login}: Displays the login form.</li>
 *     <li>{@code /register}: Displays the registration form.</li>
 *     <li>{@code /register/save}: Handles user registration and saves new users.</li>
 *     <li>{@code /users}: Lists all users.</li>
 *     <li>{@code /users/edit/{id}}: Displays a form for editing a specific user.</li>
 *     <li>{@code /users/delete/{id}}: Deletes a specific user.</li>
 *     <li>{@code /profile}: Displays the profile of the currently logged-in user.</li>
 *     <li>{@code /profile/edit}: Handles profile updates for the currently logged-in user.</li>
 *     <li>{@code /profile/delete}: Deletes the currently logged-in user's profile.</li>
 * </ul>
 *
 * @see User
 * @see UserDto
 */

@Controller
public class AuthorizationController {

    private final UserService userService;

    /**
     * Constructs an {@code AuthorizationController} with the specified user service.
     *
     * @param userService the service for handling user-related operations
     */
    @Autowired
    public AuthorizationController(UserService userService) {

        this.userService = userService;
    }

    /**
     * Handles GET requests to display the home page.
     *
     * @return the name of the view template to render the home page
     */
    @GetMapping("index")
    public String index() {
        return "index";
    }

    /**
     * Displays the login form.
     *
     * @return the name of the view template to render the login form
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * Displays the registration form.
     *
     * @param model the model to be used by the view
     * @return the name of the view template to render the registration form
     */
    @GetMapping("/register")
    public String registerForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    /**
     * Handles POST requests to save a new user during registration.
     *
     * @param user the user data for registration
     * @param result binding result to capture validation errors
     * @param model the model to be used by the view
     * @return a redirect URL to the registration success page or the same form in case of errors
     * @throws IOException if an error occurs during file handling (if uncommented)
     */
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
//                               @RequestParam("profileImage") MultipartFile profile_img,
                               BindingResult result, Model model) throws IOException {
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

    /**
     * Lists all users.
     *
     * @param model the model to be used by the view
     * @return the name of the view template to render the list of users
     */
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<UserDto> users = userService.getUsers();
        model.addAttribute("users", users);
        return "users";
    }

    /**
     * Displays the form for editing a specific user.
     *
     * @param id the ID of the user to be edited
     * @param model the model to be used by the view
     * @return the name of the view template to render the edit user form
     */
    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        UserDto userDto = userService.convertToDto(user);
        model.addAttribute("user", userDto);
        return "edit_user";
    }

//    @PostMapping("/users/update/{id}")
//    public String updateUser(@PathVariable("id") Long id, @Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("user", userDto);
//            return "edit_user";
//        }
//        userService.updateUser(userDto);
//        return "redirect:/users?success";
//    }

    /**
     * Handles GET requests to delete a specific user.
     *
     * @param id the ID of the user to be deleted
     * @return a redirect URL to the users list with a deletion status
     */
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users?deleted";
    }

    /**
     * Displays the profile of the currently logged-in user.
     *
     * @param model the model to be used by the view
     * @return the name of the view template to render the user profile
     */
    @GetMapping("/profile")
    public String profile(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        UserDto userDto = userService.convertToDto(user);
        model.addAttribute("user", userDto);
        return "profile";
    }

    /**
     * Handles POST requests to update the profile of the currently logged-in user.
     *
     * @param userDto the updated user data
     * @param result binding result to capture validation errors
     * @param model the model to be used by the view
     * @return a redirect URL to the profile page with an update status
     * @throws IOException if an error occurs during file handling (if uncommented)
     */
    @PostMapping("/profile/edit")
    public String editProfile(@Valid @ModelAttribute("user") UserDto userDto,
//                              @RequestParam("profileImage") MultipartFile profile_img,
                              BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "profile";
        }
        userService.updateUser(userDto);
        return "redirect:/profile?success";
    }

    /**
     * Handles GET requests to delete the profile of the currently logged-in user.
     *
     * @return a redirect URL to log out after profile deletion
     */
    @GetMapping("/profile/delete")
    public String deleteProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        userService.deleteUser(user.getId());
        return "redirect:/logout";
    }


//    @GetMapping("/image/{id}")
//    @ResponseBody
//    public byte[] getAnimalImage(@PathVariable Long id) {
//        User user = userService.findById(id);
//        return user.getProfile_img();
//    }
}
