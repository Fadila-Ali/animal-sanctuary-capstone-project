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
import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.service.AnimalService;
import com.ali.animalsanctuary.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Controller for handling requests related to animals in the animal sanctuary.
 *
 * <p>This class provides endpoints for listing animals, adding new animals,
 * editing existing animals, viewing details, deleting animals, and handling adoption actions.
 * It also allows for image uploads and retrieval of animal images.
 *
 * <p>Endpoints:
 * <ul>
 *     <li>{@code /animals}: Lists all animals.</li>
 *     <li>{@code /animals/add}: Displays a form for adding a new animal.</li>
 *     <li>{@code /animals/save}: Saves a new animal with optional image upload.</li>
 *     <li>{@code /animals/details/{id}}: Displays details of a specific animal.</li>
 *     <li>{@code /animals/edit/{id}}: Displays a form for editing an existing animal.</li>
 *     <li>{@code /animals/update/{id}}: Updates the details of an existing animal with optional image upload.</li>
 *     <li>{@code /animals/{id}}: Deletes a specific animal.</li>
 *     <li>{@code /animals/image/{id}}: Retrieves the image of a specific animal.</li>
 *     <li>{@code /animals/adopt/{id}}: Allows a user to adopt a specific animal.</li>
 * </ul>
 *
 * @see Animal
 * @see User
 */

@Controller
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;
    private final UserService userService;

    /**
     * Constructs an {@code AnimalController} with the specified services.
     *
     * @param animalService the service for handling animal-related operations
     * @param userService the service for handling user-related operations
     */
    public AnimalController(AnimalService animalService, UserService userService) {
        this.animalService = animalService;
        this.userService = userService;
    }

    /**
     * Handles GET requests to list all animals.
     *
     * @param model the model to be used by the view
     * @return the name of the view template to render the list of animals
     */
    @GetMapping
    public String getAnimals(Model model) {
        List<Animal> animals = animalService.findAllAnimals();
        model.addAttribute("animals", animals);
        return "animals";
    }

    /**
     * Displays a form for adding a new animal.
     *
     * @param model the model to be used by the view
     * @return the name of the view template to display the add animal form
     */
    @GetMapping("/add")
    public String showAddAnimalForm(Model model) {
        model.addAttribute("animal", new Animal());
        return "add-animal";
    }

    /**
     * Handles POST requests to save a new animal.
     *
     * @param animal the animal to be saved
     * @param result binding result to capture validation errors
     * @param file the image file to be associated with the animal
     * @param model the model to be used by the view
     * @return a redirect URL to the animals list or the same form in case of errors
     */
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

    /**
     * Displays details of a specific animal.
     *
     * @param id the ID of the animal
     * @param model the model to be used by the view
     * @return the name of the view template to display the animal details
     */
    @GetMapping("/details/{id}")
    public String getAnimalDetails(@PathVariable Long id, Model model) {
        Animal animal = animalService.findAnimalById(id);
        model.addAttribute("animal", animal);
        return "animal-details";
    }

    /**
     * Displays a form for editing an existing animal.
     *
     * @param id the ID of the animal
     * @param model the model to be used by the view
     * @return the name of the view template to display the edit animal form
     */
    @GetMapping("/edit/{id}")
    public String showEditAnimalForm(@PathVariable Long id, Model model) {
        Animal animal = animalService.findAnimalById(id);
        model.addAttribute("animal", animal);
        return "edit-animal";
    }

    /**
     * Handles POST requests to update the details of an existing animal.
     *
     * @param id the ID of the animal to be updated
     * @param animal the updated animal data
     * @param result binding result to capture validation errors
     * @param file the image file to be associated with the animal
     * @param model the model to be used by the view
     * @return a redirect URL to the animal details or the same form in case of errors
     */
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

    /**
     * Handles POST requests to delete a specific animal.
     *
     * @param id the ID of the animal to be deleted
     * @return a redirect URL to the animals list
     */
    @PostMapping("/{id}")
    public String deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return "redirect:/animals";
    }

    /**
     * Retrieves the image of a specific animal.
     *
     * @param id the ID of the animal
     * @return the image data as a byte array
     */
    @GetMapping("/image/{id}")
    @ResponseBody
    public byte[] getAnimalImage(@PathVariable Long id) {
        Animal animal = animalService.findAnimalById(id);
        return animal.getImage();
    }

    /**
     * Handles POST requests for adopting a specific animal.
     *
     * @param id the ID of the animal to be adopted
     * @param userDetails the details of the authenticated user
     * @return a redirect URL to the animal details
     */
    @PostMapping("/adopt/{id}")
    public String adoptAnimal(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("UserDetails is null");
        }
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        animalService.adoptAnimal(id, user.getId());
        System.out.println("adopted" + id + "adopter" + username);
        return "redirect:/animals/details/" + id;
    }

}

