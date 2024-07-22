package com.ali.animalsanctuary.controller;

import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.entity.Volunteer;
import com.ali.animalsanctuary.service.UserService;
import com.ali.animalsanctuary.service.VolunteerService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


//@Controller
//@RequestMapping("/volunteers")
//public class VolunteerController {
//
//    private static final Logger logger = LoggerFactory.getLogger(VolunteerController.class);
//
//    @Autowired
//    private VolunteerService volunteerService;
//
//    @GetMapping
//    public String getAllVolunteerWorks(Model model) {
//        logger.info("Getting all volunteer works");
//        model.addAttribute("volunteerWorks", volunteerService.getAllVolunteers());
//        return "volunteers";
//    }
//
//    @GetMapping("/{id}")
//    public String getVolunteerDetails(@PathVariable Long id, Model model) {
//        logger.info("Getting details for volunteer with id: {}", id);
//        model.addAttribute("volunteer", volunteerService.getVolunteerById(id));
//        return "volunteer-details";
//    }
//
//    @GetMapping("/add")
//    public String showAddVolunteerForm(Model model) {
//        logger.info("Showing add volunteer form");
//        model.addAttribute("volunteer", new Volunteer());
//        return "add-volunteer";
//    }
//
//    @PostMapping("/save")
//    public String createVolunteerWork(@Valid @ModelAttribute Volunteer volunteer, BindingResult result, Model model) {
//        logger.info("Creating new volunteer work");
//        if (result.hasErrors()) {
//            logger.error("Validation errors occurred: {}", result.getAllErrors());
//            model.addAttribute("volunteer", volunteer);
//            return "add-volunteer";
//        }
//        volunteerService.saveVolunteer(volunteer);
//        return "redirect:/volunteers";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String showEditVolunteerForm(@PathVariable Long id, Model model) {
//        logger.info("Showing edit form for volunteer with id: {}", id);
//        model.addAttribute("volunteer", volunteerService.getVolunteerById(id));
//        return "edit-volunteer";
//    }
//
//    @PostMapping("/update/{id}")
//    public String updateVolunteerWork(@PathVariable Long id, @Valid @ModelAttribute Volunteer volunteer, BindingResult result, Model model) {
//        logger.info("Updating volunteer work with id: {}", id);
//        if (result.hasErrors()) {
//            logger.error("Validation errors occurred: {}", result.getAllErrors());
//            model.addAttribute("volunteer", volunteer);
//            return "edit-volunteer";
//        }
//        volunteer.setId(id);
//        volunteerService.saveVolunteer(volunteer);
//        logger.info("Volunteer work updated successfully");
//        return "redirect:/volunteers";
//    }
//
//    @PostMapping("/delete/{id}")
//    public String deleteVolunteerWork(@PathVariable Long id) {
//        logger.info("Deleting volunteer work with id: {}", id);
//        volunteerService.deleteVolunteer(id);
//        return "redirect:/volunteers";
//    }
//
//    @PostMapping("/volunteer/{id}")
//    public String volunteer(@PathVariable Long id, @AuthenticationPrincipal User user) {
//        logger.info("User {} volunteering for work with id: {}", user.getUsername(), id);
//        // Logic to add the user as a volunteer for the volunteer work
//        return "redirect:/volunteers";
//    }
//}


import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/volunteers")
public class VolunteerController {

    private static final Logger logger = LoggerFactory.getLogger(VolunteerController.class);

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllVolunteerWorks(Model model) {
        logger.info("Getting all volunteer works");
        model.addAttribute("volunteerWorks", volunteerService.getAllVolunteers());
        return "volunteers";
    }

    @GetMapping("/{id}")
    public String getVolunteerDetails(@PathVariable Long id, Model model) {
        logger.info("Getting details for volunteer with id: {}", id);
        model.addAttribute("volunteer", volunteerService.getVolunteerById(id));
        return "volunteer-details";
    }

    @PostMapping("/volunteer/{id}")
    @ResponseBody
    public Map<String, Object> volunteer(@PathVariable Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        Map<String, Object> response = new HashMap<>();
        try {
            Volunteer volunteer = volunteerService.getVolunteerById(id);
            if (volunteer != null) {
                Optional<User> optionalUser = userService.findByUsername(principal.getUsername());
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    volunteerService.addUserToVolunteer(id, user);
                    response.put("success", true);
                } else {
                    response.put("success", false);
                    response.put("message", "User not found.");
                }
            } else {
                response.put("success", false);
                response.put("message", "Volunteer work not found.");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

    @GetMapping("/add")
    public String showAddVolunteerForm(Model model) {
        logger.info("Showing add volunteer form");
        model.addAttribute("volunteer", new Volunteer());
        return "add-volunteer";
    }

    @PostMapping("/save")
    public String createVolunteerWork(@Valid @ModelAttribute Volunteer volunteer, BindingResult result, Model model) {
        logger.info("Creating new volunteer work");
        if (result.hasErrors()) {
            logger.error("Validation errors occurred: {}", result.getAllErrors());
            model.addAttribute("volunteer", volunteer);
            return "add-volunteer";
        }
        volunteerService.saveVolunteer(volunteer);
        return "redirect:/volunteers";
    }

    @GetMapping("/edit/{id}")
    public String showEditVolunteerForm(@PathVariable Long id, Model model) {
        logger.info("Showing edit form for volunteer with id: {}", id);
        Volunteer volunteer = volunteerService.getVolunteerById(id);
        model.addAttribute("volunteer", volunteer);
        return "edit-volunteer";
    }

    @PostMapping("/update/{id}")
    public String updateVolunteerWork(@PathVariable Long id, @Valid @ModelAttribute Volunteer volunteer, BindingResult result, Model model) {
        logger.info("Updating volunteer work with id: {}", id);
        if (result.hasErrors()) {
            logger.error("Validation errors occurred: {}", result.getAllErrors());
            model.addAttribute("volunteer", volunteer);
            return "edit-volunteer";
        }
        volunteer.setId(id);
        volunteerService.saveVolunteer(volunteer);
        return "redirect:/volunteers";
    }

    @PostMapping("/delete/{id}")
    public String deleteVolunteerWork(@PathVariable Long id) {
        logger.info("Deleting volunteer work with id: {}", id);
        volunteerService.deleteVolunteer(id);
        return "redirect:/volunteers";
    }
}
