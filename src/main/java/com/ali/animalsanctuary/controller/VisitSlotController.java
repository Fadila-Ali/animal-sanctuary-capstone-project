//package com.ali.animalsanctuary.controller;
//
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.DayOfWeek;
//import java.util.List;
//
//@Controller
//@RequestMapping("/visits")
//public class VisitSlotController {
//
//    @Autowired
//    private VisitSlotService visitSlotService;
//
//    @GetMapping
//    public String getAvailableSlots(Model model, @RequestParam(required = false) DayOfWeek dayOfWeek) {
//        List<VisitSlot> visitSlots;
//        if (dayOfWeek != null) {
//            visitSlots = visitSlotService.getAvailableSlots(dayOfWeek);
//        } else {
//            visitSlots = visitSlotService.getAllVisitSlots();
//        }
//        model.addAttribute("visitSlots", visitSlots);
//        return "visits";
//    }
//
//    @PostMapping("/save")
//    public String createVisitSlot(Model model, @Valid VisitSlot visitSlot, BindingResult result) {
//        if (result.hasErrors()) {
//            model.addAttribute("visitSlot", visitSlot);
//            return "add-visit-slot";
//        }
//        visitSlotService.saveVisitSlot(visitSlot);
//        model.addAttribute("visitSlot", visitSlot);
//        return "redirect:/visits";
//    }
//
//    @GetMapping("/add")
//    public String showAddVisitSlotForm(Model model) {
//        model.addAttribute("visitSlot", new VisitSlot());
//        return "add-visit-slot";
//    }
//
//    @PutMapping("/update-availability/{id}")
//    public String updateAvailability(@PathVariable Long id, @RequestParam boolean isAvailable) {
//        visitSlotService.updateAvailability(id, isAvailable);
//        return "redirect:/visits";
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteVisitSlot(@PathVariable Long id) {
//        visitSlotService.deleteVisitSlot(id);
//        return "redirect:/visits";
//    }
//}
