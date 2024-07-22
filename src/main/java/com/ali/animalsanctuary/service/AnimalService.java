package com.ali.animalsanctuary.service;

import com.ali.animalsanctuary.entity.Animal;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AnimalService {

    Animal saveAnimal(Animal animal, MultipartFile image) throws IOException;

    Animal updateAnimal(Long id, Animal animal, MultipartFile image) throws IOException;

    void deleteAnimal(Long id);

    Animal findAnimalById(Long id);

    List<Animal> findAllAnimals();
}
