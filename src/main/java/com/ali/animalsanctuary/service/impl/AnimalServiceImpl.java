//package com.ali.animalsanctuary.service.impl;
//
//import com.ali.animalsanctuary.entity.Animal;
//import com.ali.animalsanctuary.entity.AnimalImage;
//import com.ali.animalsanctuary.repository.AnimalRepository;
//import com.ali.animalsanctuary.service.AnimalService;
//import com.ali.animalsanctuary.service.AnimalImageService;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@Service
//public class AnimalServiceImpl implements AnimalService {
//
//    private final AnimalRepository animalRepository;
//    private final AnimalImageService animalImageService;
//
//    public AnimalServiceImpl(AnimalRepository animalRepository, AnimalImageService animalImageService) {
//        this.animalRepository = animalRepository;
//        this.animalImageService = animalImageService;
//    }
//
//    @Override
//    public Animal saveAnimal(String name, String species, String breed, String gender, int age, String adoptionStatus, String description, MultipartFile image) throws IOException {
//        AnimalImage animalImage = animalImageService.saveImage(image);
//        Animal animal = new Animal(null, name, species, breed, gender, age, adoptionStatus, description, animalImage);
//        return animalRepository.save(animal);
//    }
//
//    @Override
//    public Animal updateAnimal(Long id, String name, String species, String breed, String gender, int age, String adoptionStatus, String description, MultipartFile image) throws IOException {
//        Animal animal = animalRepository.findById(id).orElseThrow(() -> new RuntimeException("Animal not found"));
//        animal.setName(name);
//        animal.setSpecies(species);
//        animal.setBreed(breed);
//        animal.setGender(gender);
//        animal.setAge(age);
//        animal.setAdoptionStatus(adoptionStatus);
//        animal.setDescription(description);
//
//        if (image != null && !image.isEmpty()) {
//            AnimalImage animalImage = animalImageService.saveImage(image);
//            animal.setImage(animalImage);
//        }
//
//        return animalRepository.save(animal);
//    }
//
//    @Override
//    public void deleteAnimal(Long id) {
//        animalRepository.deleteById(id);
//    }
//
//    @Override
//    public Animal findAnimalById(Long id) {
//        return animalRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    public List<Animal> findAllAnimals() {
//        return animalRepository.findAll();
//    }
//}


package com.ali.animalsanctuary.service.impl;

import com.ali.animalsanctuary.entity.Animal;
import com.ali.animalsanctuary.entity.User;
import com.ali.animalsanctuary.repository.AnimalRepository;
import com.ali.animalsanctuary.repository.UserRepository;
import com.ali.animalsanctuary.service.AnimalService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Implementation of the {@link AnimalService} interface.
 *
 * This class provides the actual implementation of the methods defined in the {@link AnimalService} interface,
 * handling operations related to {@link Animal} entities, including saving, updating, deleting, and retrieving animals.
 * It also supports adopting animals and handling image files associated with animals.
 *
 * <p>Methods:
 * <ul>
 *     <li>{@link #saveAnimal(Animal, MultipartFile)}: Saves a new {@link Animal} entity with an optional image.</li>
 *     <li>{@link #updateAnimal(Long, Animal, MultipartFile)}: Updates an existing {@link Animal} entity with optional new image data.</li>
 *     <li>{@link #deleteAnimal(Long)}: Deletes the {@link Animal} entity with the specified ID.</li>
 *     <li>{@link #findAnimalById(Long)}: Retrieves the {@link Animal} entity with the specified ID.</li>
 *     <li>{@link #findAllAnimals()} : Retrieves a list of all {@link Animal} entities.</li>
 *     <li>{@link #adoptAnimal(Long, Long)}: Processes the adoption of an {@link Animal} by a {@link User} with the given ID.</li>
 * </ul>
 *
 * @see Animal
 * @see User
 * @see AnimalService
 */

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a new {@link AnimalServiceImpl} with the specified {@link AnimalRepository} and {@link UserRepository}.
     *
     * @param animalRepository the repository for managing {@link Animal} entities.
     * @param userRepository   the repository for managing {@link User} entities.
     */
    public AnimalServiceImpl(AnimalRepository animalRepository, UserRepository userRepository) {
        this.animalRepository = animalRepository;
        this.userRepository = userRepository;
    }

    /**
     * Saves a new {@link Animal} entity with an optional image.
     *
     * @param animal the {@link Animal} entity to be saved.
     * @param image  the image file to be associated with the animal (can be {@code null} or empty).
     * @return the saved {@link Animal} entity.
     * @throws IOException if an I/O error occurs during image processing.
     */
    @Override
    public Animal saveAnimal(Animal animal, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            animal.setImage(image.getBytes());
        }
        return animalRepository.save(animal);
    }

    /**
     * Updates an existing {@link Animal} entity with optional new image data.
     *
     * @param id     the ID of the {@link Animal} entity to be updated.
     * @param animal the updated {@link Animal} entity.
     * @param image  the new image file to be associated with the animal (can be {@code null} or empty).
     * @return the updated {@link Animal} entity.
     * @throws IOException if an I/O error occurs during image processing.
     */
    @Override
    public Animal updateAnimal(Long id, Animal animal, MultipartFile image) throws IOException {
        Animal existingAnimal = animalRepository.findById(id).orElseThrow(() -> new RuntimeException("Animal not found"));
        existingAnimal.setName(animal.getName());
        existingAnimal.setSpecies(animal.getSpecies());
        existingAnimal.setBreed(animal.getBreed());
        existingAnimal.setGender(animal.getGender());
        existingAnimal.setAge(animal.getAge());
        existingAnimal.setAdoptionStatus(animal.getAdoptionStatus());
        existingAnimal.setDescription(animal.getDescription());

        if (image != null && !image.isEmpty()) {
            existingAnimal.setImage(image.getBytes());
        }
        return animalRepository.save(existingAnimal);
    }

    /**
     * Deletes the {@link Animal} entity with the specified ID.
     *
     * @param id the ID of the {@link Animal} entity to be deleted.
     */
    @Override
    public void deleteAnimal(Long id) {
        animalRepository.deleteById(id);
    }

    /**
     * Retrieves the {@link Animal} entity with the specified ID.
     *
     * @param id the ID of the {@link Animal} entity to be retrieved.
     * @return the {@link Animal} entity with the specified ID.
     * @throws RuntimeException if the animal with the specified ID is not found.
     */
    @Override
    public Animal findAnimalById(Long id) {
        return animalRepository.findById(id).orElseThrow(() -> new RuntimeException("Animal not found"));
    }

    /**
     * Retrieves a list of all {@link Animal} entities.
     *
     * @return a list of all {@link Animal} entities.
     */
    @Override
    public List<Animal> findAllAnimals() {
        return animalRepository.findAll();
    }

    /**
     * Processes the adoption of an {@link Animal} by a {@link User} with the given ID.
     *
     * @param animalId the ID of the {@link Animal} entity to be adopted.
     * @param userId   the ID of the {@link User} adopting the animal.
     * @return the updated {@link Animal} entity after adoption.
     * @throws RuntimeException if the animal or user with the specified IDs is not found.
     */
    @Override
    @Transactional
    public Animal adoptAnimal(Long animalId, Long userId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(() -> new RuntimeException("Animal not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("Adopting animal: " + animalId + " by user: " + userId);

        animal.setAdopted(true);
        animal.setAdopter(user);
        animalRepository.save(animal);

        System.out.println("Animal adopted successfully");

        return animal;
    }
}
