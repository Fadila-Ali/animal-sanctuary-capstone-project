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

@Controller
@RequestMapping("/visits")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllVisits(Model model) {
        List<Visit> visits = visitService.getAvailableVisits();
        model.addAttribute("visits", visits);
        return "visits";
    }

    @GetMapping("/details/{id}")
    public String getVisitDetails(@PathVariable Long id, Model model) {
        Optional<Visit> visit = visitService.getVisitById(id);
        visit.ifPresent(v -> model.addAttribute("visit", v));
        return "visit-details";
    }

    @PostMapping("/book/{id}")
    public String bookVisit(@PathVariable Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        Optional<User> user = userService.findByUsername(principal.getUsername());
        if (user.isPresent()) {
            visitService.bookVisit(id, user.get().getId());
        }
        return "redirect:/visits";
    }

    @PostMapping("/cancel/{id}")
    public String cancelVisit(@PathVariable Long id) {
        visitService.cancelVisit(id);
        return "redirect:/visits";
    }

    @GetMapping("/add")
    public String showAddVisitForm(Model model) {
        model.addAttribute("visit", new VisitDto());
        return "add-visit";
    }

    @PostMapping("/save")
    public String createVisit(@ModelAttribute VisitDto visitDto) {
        visitService.createVisit(visitDto);
        return "redirect:/visits";
    }

    @GetMapping("/edit/{id}")
    public String showEditVisitForm(@PathVariable Long id, Model model) {
        Optional<Visit> visit = visitService.getVisitById(id);
        visit.ifPresent(v -> model.addAttribute("visit", v));
        return "edit-visit";
    }

    @PostMapping("/update")
    public String updateVisit(@ModelAttribute VisitDto visitDto) {
        visitService.updateVisit(visitDto);
        return "redirect:/visits";
    }

    @PostMapping("/delete/{id}")
    public String deleteVisit(@PathVariable Long id) {
        visitService.deleteVisit(id);
        return "redirect:/visits";
    }
}
