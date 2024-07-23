package com.ali.animalsanctuary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents an animal entity in the Animal Sanctuary application.
 * Each animal has attributes such as name, species, breed, gender, age, adoption status, description, and image.
 * The entity is annotated for use with JPA/Hibernate.
 *
 * Annotations:
 * {@code @Entity} - Specifies that this class is an entity and is mapped to a database table.
 * {@code @Table(name = "animals")} - Specifies the name of the database table to be used for mapping.
 * {@code @Data} - Lombok annotation to generate boilerplate code such as getters, setters, toString, etc.
 * {@code @NoArgsConstructor} - Lombok annotation to generate a no-arguments constructor.
 * {@code @AllArgsConstructor} - Lombok annotation to generate an all-arguments constructor.
 *
 * Fields:
 * {@code id} - The unique identifier for the animal.
 * {@code name} - The name of the animal.
 * {@code species} - The species of the animal.
 * {@code breed} - The breed of the animal.
 * {@code gender} - The gender of the animal.
 * {@code age} - The age of the animal.
 * {@code adoptionStatus} - The adoption status of the animal.
 * {@code description} - A description of the animal.
 * {@code image} - A byte array representing the image of the animal.
 * {@code adopted} - A boolean indicating whether the animal is adopted.
 * {@code adopter} - The user who adopted the animal.
 *
 * Relationships:
 * {@code @ManyToOne} - Defines a many-to-one relationship with the {@code User} entity.
 */

@Entity
@Table(name = "animals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String species;
    private String breed;
    private String gender;
    private int age;
    private String adoptionStatus;
    private String description;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "animal_image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(name = "is_adopted")
    private boolean adopted;

    @ManyToOne
    @JoinColumn(name = "adopter_id")
    private User adopter;
}
