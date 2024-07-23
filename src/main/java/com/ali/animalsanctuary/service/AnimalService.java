package com.ali.animalsanctuary.service;

import com.ali.animalsanctuary.entity.Animal;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Service interface for managing {@link Animal} entities.
 *
 * This interface defines the operations available for handling animal records, including saving, updating,
 * deleting, and retrieving animals. It also supports animal adoption and image handling.
 *
 * <p>Methods:
 * <ul>
 *     <li>{@link #saveAnimal(Animal, MultipartFile)}: Saves a new {@link Animal} entity with an associated image.</li>
 *     <li>{@link #updateAnimal(Long, Animal, MultipartFile)}: Updates an existing {@link Animal} entity with a new image.</li>
 *     <li>{@link #deleteAnimal(Long)}: Deletes the {@link Animal} entity with the specified ID.</li>
 *     <li>{@link #findAnimalById(Long)}: Retrieves the {@link Animal} entity with the specified ID.</li>
 *     <li>{@link #findAllAnimals()} : Retrieves a list of all {@link Animal} entities.</li>
 *     <li>{@link #adoptAnimal(Long, Long)}: Processes the adoption of an {@link Animal} by a {@link} with the given ID.</li>
 * </ul>
 *
 * @see Animal
 */

public interface AnimalService {

    /**
     * Saves a new {@link Animal} entity with an associated image.
     *
     * @param animal the {@link Animal} entity to be saved.
     * @param image  the image file to be associated with the animal.
     * @return the saved {@link Animal} entity.
     * @throws IOException if an I/O error occurs during file handling.
     */
    Animal saveAnimal(Animal animal, MultipartFile image) throws IOException;

    /**
     * Updates an existing {@link Animal} entity with a new image.
     *
     * @param id     the ID of the {@link Animal} entity to be updated.
     * @param animal the updated {@link Animal} entity.
     * @param image  the new image file to be associated with the animal.
     * @return the updated {@link Animal} entity.
     * @throws IOException if an I/O error occurs during file handling.
     */
    Animal updateAnimal(Long id, Animal animal, MultipartFile image) throws IOException;

    /**
     * Deletes the {@link Animal} entity with the specified ID.
     *
     * @param id the ID of the {@link Animal} entity to be deleted.
     */
    void deleteAnimal(Long id);

    /**
     * Retrieves the {@link Animal} entity with the specified ID.
     *
     * @param id the ID of the {@link Animal} entity to be retrieved.
     * @return the {@link Animal} entity with the specified ID.
     */
    Animal findAnimalById(Long id);

    /**
     * Retrieves a list of all {@link Animal} entities.
     *
     * @return a list of all {@link Animal} entities.
     */
    List<Animal> findAllAnimals();

    /**
     * Processes the adoption of an {@link Animal} by a {@link} with the given ID.
     *
     * @param id     the ID of the {@link Animal} entity to be adopted.
     * @param userId the ID of the {@link} adopting the animal.
     * @return the adopted {@link Animal} entity.
     */
    Animal adoptAnimal(Long id, Long userId);
}
