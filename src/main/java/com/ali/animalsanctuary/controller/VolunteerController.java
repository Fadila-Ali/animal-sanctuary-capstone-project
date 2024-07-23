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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for managing volunteer works within the animal sanctuary.
 *
 * <p>This class provides endpoints for viewing, adding, editing, and deleting volunteer works. It also allows
 * users to volunteer for specific tasks.
 *
 * <p>Endpoints:
 * <ul>
 *     <li>{@code /volunteers}: Displays a list of all volunteer works.</li>
 *     <li>{@code /volunteers/{id}}: Displays details of a specific volunteer work.</li>
 *     <li>{@code /volunteers/add}: Displays the form for adding a new volunteer work.</li>
 *     <li>{@code /volunteers/save}: Handles the creation of a new volunteer work.</li>
 *     <li>{@code /volunteers/edit/{id}}: Displays the form for editing a specific volunteer work.</li>
 *     <li>{@code /volunteers/update/{id}}: Handles the update of a specific volunteer work.</li>
 *     <li>{@code /volunteers/delete/{id}}: Deletes a specific volunteer work.</li>
 *     <li>{@code /volunteers/volunteer/{id}}: Allows the currently logged-in user to volunteer for a specific task.</li>
 * </ul>
 *
 * @see Volunteer
 * @see User
 */

@Controller
@RequestMapping("/volunteers")
public class VolunteerController {

    private static final Logger logger = LoggerFactory.getLogger(VolunteerController.class);

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private UserService userService;

    /**
     * Handles GET requests to display a list of all volunteer works.
     *
     * @param model the model to be used by the view
     * @return the name of the view template to render the list of volunteer works
     */
    @GetMapping
    public String getAllVolunteerWorks(Model model) {
        logger.info("Getting all volunteer works");
        model.addAttribute("volunteerWorks", volunteerService.getAllVolunteers());
        return "volunteers";
    }

    /**
     * Displays the details of a specific volunteer work.
     *
     * @param id the ID of the volunteer work to be displayed
     * @param model the model to be used by the view
     * @return the name of the view template to render the volunteer work details
     */
    @GetMapping("/{id}")
    public String getVolunteerDetails(@PathVariable Long id, Model model) {
        logger.info("Getting details for volunteer with id: {}", id);
        model.addAttribute("volunteer", volunteerService.getVolunteerById(id));
        return "volunteer-details";
    }

    /**
     * Allows the currently logged-in user to volunteer for a specific task.
     *
     * @param id the ID of the volunteer work
     * @param principal the currently logged-in user
     * @return a map containing the result of the operation
     */
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

    /**
     * Displays the form for adding a new volunteer work.
     *
     * @param model the model to be used by the view
     * @return the name of the view template to render the add volunteer form
     */
    @GetMapping("/add")
    public String showAddVolunteerForm(Model model) {
        logger.info("Showing add volunteer form");
        model.addAttribute("volunteer", new Volunteer());
        return "add-volunteer";
    }

    /**
     * Handles POST requests to create a new volunteer work.
     *
     * @param volunteer the data for the new volunteer work
     * @param result the result of the validation process
     * @param model the model to be used by the view
     * @return a redirect URL to the list of volunteer works or the add form if validation fails
     */
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

    /**
     * Displays the form for editing a specific volunteer work.
     *
     * @param id the ID of the volunteer work to be edited
     * @param model the model to be used by the view
     * @return the name of the view template to render the edit volunteer form
     */
    @GetMapping("/edit/{id}")
    public String showEditVolunteerForm(@PathVariable Long id, Model model) {
        logger.info("Showing edit form for volunteer with id: {}", id);
        Volunteer volunteer = volunteerService.getVolunteerById(id);
        model.addAttribute("volunteer", volunteer);
        return "edit-volunteer";
    }

    /**
     * Handles POST requests to update a specific volunteer work.
     *
     * @param id the ID of the volunteer work to be updated
     * @param volunteer the updated data for the volunteer work
     * @param result the result of the validation process
     * @param model the model to be used by the view
     * @return a redirect URL to the list of volunteer works or the edit form if validation fails
     */
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

    /**
     * Deletes a specific volunteer work.
     *
     * @param id the ID of the volunteer work to be deleted
     * @return a redirect URL to the list of volunteer works
     */
    @PostMapping("/delete/{id}")
    public String deleteVolunteerWork(@PathVariable Long id) {
        logger.info("Deleting volunteer work with id: {}", id);
        volunteerService.deleteVolunteer(id);
        return "redirect:/volunteers";
    }
}
