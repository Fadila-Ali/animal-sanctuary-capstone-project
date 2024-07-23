package com.ali.animalsanctuary.controller;

import com.ali.animalsanctuary.dto.VisitDto;
import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.entity.Visit;
import com.ali.animalsanctuary.service.UserService;
import com.ali.animalsanctuary.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing visits within the animal sanctuary.
 *
 * <p>This class provides endpoints for viewing, booking, canceling, and managing visits. It also allows for
 * the addition, editing, and deletion of visit records.
 *
 * <p>Endpoints:
 * <ul>
 *     <li>{@code /visits}: Displays a list of all available visits.</li>
 *     <li>{@code /visits/details/{id}}: Displays details of a specific visit.</li>
 *     <li>{@code /visits/book/{id}}: Books a specific visit for the currently logged-in user.</li>
 *     <li>{@code /visits/cancel/{id}}: Cancels a specific visit.</li>
 *     <li>{@code /visits/add}: Displays the form for adding a new visit.</li>
 *     <li>{@code /visits/save}: Handles the creation of a new visit.</li>
 *     <li>{@code /visits/edit/{id}}: Displays the form for editing a specific visit.</li>
 *     <li>{@code /visits/update}: Handles the update of a specific visit.</li>
 *     <li>{@code /visits/delete/{id}}: Deletes a specific visit.</li>
 * </ul>
 *
 * @see Visit
 * @see VisitDto
 * @see User
 */

@Controller
@RequestMapping("/visits")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @Autowired
    private UserService userService;

    /**
     * Handles GET requests to display a list of all available visits.
     *
     * @param model the model to be used by the view
     * @return the name of the view template to render the list of visits
     */
    @GetMapping
    public String getAllVisits(Model model) {
        List<Visit> visits = visitService.getAvailableVisits();
        model.addAttribute("visits", visits);
        return "visits";
    }

    /**
     * Displays the details of a specific visit.
     *
     * @param id the ID of the visit to be displayed
     * @param model the model to be used by the view
     * @return the name of the view template to render the visit details
     */
    @GetMapping("/details/{id}")
    public String getVisitDetails(@PathVariable Long id, Model model) {
        Optional<Visit> visit = visitService.getVisitById(id);
        visit.ifPresent(v -> model.addAttribute("visit", v));
        return "visit-details";
    }

    /**
     * Books a specific visit for the currently logged-in user.
     *
     * @param id the ID of the visit to be booked
     * @param principal the currently logged-in user
     * @return a redirect URL to the visits list
     */
    @PostMapping("/book/{id}")
    public String bookVisit(@PathVariable Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        Optional<User> user = userService.findByUsername(principal.getUsername());
        if (user.isPresent()) {
            visitService.bookVisit(id, user.get().getId());
        }
        return "redirect:/visits";
    }

    /**
     * Cancels a specific visit.
     *
     * @param id the ID of the visit to be canceled
     * @return a redirect URL to the visits list
     */
    @PostMapping("/cancel/{id}")
    public String cancelVisit(@PathVariable Long id) {
        visitService.cancelVisit(id);
        return "redirect:/visits";
    }

    /**
     * Displays the form for adding a new visit.
     *
     * @param model the model to be used by the view
     * @return the name of the view template to render the add visit form
     */
    @GetMapping("/add")
    public String showAddVisitForm(Model model) {
        model.addAttribute("visit", new VisitDto());
        return "add-visit";
    }

    /**
     * Handles POST requests to create a new visit.
     *
     * @param visitDto the data for the new visit
     * @return a redirect URL to the list of visits
     */
    @PostMapping("/save")
    public String createVisit(@ModelAttribute VisitDto visitDto) {
        visitService.createVisit(visitDto);
        return "redirect:/visits";
    }

    /**
     * Displays the form for editing a specific visit.
     *
     * @param id the ID of the visit to be edited
     * @param model the model to be used by the view
     * @return the name of the view template to render the edit visit form
     */
    @GetMapping("/edit/{id}")
    public String showEditVisitForm(@PathVariable Long id, Model model) {
        Optional<Visit> visit = visitService.getVisitById(id);
        visit.ifPresent(v -> model.addAttribute("visit", v));
        return "edit-visit";
    }

    /**
     * Handles POST requests to update a specific visit.
     *
     * @param visitDto the updated data for the visit
     * @return a redirect URL to the list of visits
     */
    @PostMapping("/update")
    public String updateVisit(@ModelAttribute VisitDto visitDto) {
        visitService.updateVisit(visitDto);
        return "redirect:/visits";
    }

    /**
     * Deletes a specific visit.
     *
     * @param id the ID of the visit to be deleted
     * @return a redirect URL to the list of visits
     */
    @PostMapping("/delete/{id}")
    public String deleteVisit(@PathVariable Long id) {
        visitService.deleteVisit(id);
        return "redirect:/visits";
    }
}
