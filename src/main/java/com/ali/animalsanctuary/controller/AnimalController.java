//package com.ali.animalsanctuary.controller;
//
//import com.ali.animalsanctuary.entity.Animal;
//import com.ali.animalsanctuary.service.AnimalService;
//import jakarta.validation.Valid;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@Controller
//@RequestMapping("/animals")
//public class AnimalController {
//
//    private final AnimalService animalService;
//
//    public AnimalController(AnimalService animalService) {
//        this.animalService = animalService;
//    }
//
//    @GetMapping
//    public String getAnimals(Model model) {
//        List<Animal> animals = animalService.findAllAnimals();
//        model.addAttribute("animals", animals);
//        return "animals";
//    }
//
//    @GetMapping("/add")
//    public String showAddAnimalForm(Model model) {
//        model.addAttribute("animal", new Animal());
//        return "add-animal";
//    }
//
////    @PostMapping("/save")
////    public String saveAnimal(@ModelAttribute Animal animal, @RequestParam("image") MultipartFile imageFile) {
////        try {
////            animalService.saveAnimal(animal.getName(), animal.getSpecies(), animal.getBreed(), animal.getGender(),
////                    animal.getAge(), animal.getAdoptionStatus(), animal.getDescription(), imageFile);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        return "redirect:/animals";
////    }
//
//    @PostMapping("/animals/save")
//    public String saveAnimal(@Valid Animal animal, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("animal", animal);
//            return "add-animal";
//        }
//
//        if (!file.isEmpty()) {
//            try {
//                byte[] imageData = file.getBytes();
//                animal.setImage(imageData);
//            } catch (IOException e) {
//                e.printStackTrace();
//                // Handle the exception appropriately
//            }
//        }
//
//        animalService.saveAnimal(animal);
//        return "redirect:/animals";
//    }
//
//
//    @GetMapping("/details/{id}")
//    public String getAnimalDetails(@PathVariable Long id, Model model) {
//        Animal animal = animalService.findAnimalById(id);
//        model.addAttribute("animal", animal);
//        return "animal-details";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String showEditAnimalForm(@PathVariable Long id, Model model) {
//        Animal animal = animalService.findAnimalById(id);
//        model.addAttribute("animal", animal);
//        return "edit-animal";
//    }
//
//    @PostMapping("/update/{id}")
//    public String updateAnimal(@PathVariable Long id, @ModelAttribute Animal animal, @RequestParam("image") MultipartFile imageFile) {
//        try {
//            animalService.updateAnimal(id, animal.getName(), animal.getSpecies(), animal.getBreed(), animal.getGender(),
//                    animal.getAge(), animal.getAdoptionStatus(), animal.getDescription(), imageFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "redirect:/animals/details/" + id;
//    }
//
//    @PostMapping("/{id}")
//    public String deleteAnimal(@PathVariable Long id) {
//        animalService.deleteAnimal(id);
//        return "redirect:/animals";
//    }
//}

package com.ali.animalsanctuary.controller;

import com.ali.animalsanctuary.entity.Animal;
import com.ali.animalsanctuary.service.AnimalService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public String getAnimals(Model model) {
        List<Animal> animals = animalService.findAllAnimals();
        model.addAttribute("animals", animals);
        return "animals";
    }

    @GetMapping("/add")
    public String showAddAnimalForm(Model model) {
        model.addAttribute("animal", new Animal());
        return "add-animal";
    }

    @PostMapping("/save")
    public String saveAnimal(@Valid Animal animal, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("animal", animal);
            return "add-animal";
        }

        try {
            animalService.saveAnimal(animal, file);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error saving animal");
            return "add-animal";
        }
        return "redirect:/animals";
    }

    @GetMapping("/details/{id}")
    public String getAnimalDetails(@PathVariable Long id, Model model) {
        Animal animal = animalService.findAnimalById(id);
        model.addAttribute("animal", animal);
        return "animal-details";
    }

    @GetMapping("/edit/{id}")
    public String showEditAnimalForm(@PathVariable Long id, Model model) {
        Animal animal = animalService.findAnimalById(id);
        model.addAttribute("animal", animal);
        return "edit-animal";
    }

    @PostMapping("/update/{id}")
    public String updateAnimal(@PathVariable Long id, @Valid Animal animal, BindingResult result, @RequestParam("file") MultipartFile file, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("animal", animal);
            return "edit-animal";
        }

        try {
            animalService.updateAnimal(id, animal, file);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error updating animal");
            return "edit-animal";
        }
        return "redirect:/animals/details/" + id;
    }

    @PostMapping("/{id}")
    public String deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return "redirect:/animals";
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public byte[] getAnimalImage(@PathVariable Long id) {
        Animal animal = animalService.findAnimalById(id);
        return animal.getImage();
    }

}

