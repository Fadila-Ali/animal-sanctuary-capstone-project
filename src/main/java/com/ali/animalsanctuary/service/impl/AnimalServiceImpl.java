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
import com.ali.animalsanctuary.repository.AnimalRepository;
import com.ali.animalsanctuary.service.AnimalService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public Animal saveAnimal(Animal animal, MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            animal.setImage(image.getBytes());
        }
        return animalRepository.save(animal);
    }

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

    @Override
    public void deleteAnimal(Long id) {
        animalRepository.deleteById(id);
    }

    @Override
    public Animal findAnimalById(Long id) {
        return animalRepository.findById(id).orElseThrow(() -> new RuntimeException("Animal not found"));
    }

    @Override
    public List<Animal> findAllAnimals() {
        return animalRepository.findAll();
    }
}
