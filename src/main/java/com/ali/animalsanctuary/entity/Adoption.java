package com.ali.animalsanctuary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents an adoption entity in the Animal Sanctuary application.
 * Each adoption records the user who adopted an animal, the adopted animal, and the date of adoption.
 * The entity is annotated for use with JPA/Hibernate.
 *
 * Annotations:
 * {@code @Entity} - Specifies that this class is an entity and is mapped to a database table.
 * {@code @Table(name = "adoptions")} - Specifies the name of the database table to be used for mapping.
 * {@code @Data} - Lombok annotation to generate boilerplate code such as getters, setters, toString, etc.
 * {@code @NoArgsConstructor} - Lombok annotation to generate a no-arguments constructor.
 * {@code @AllArgsConstructor} - Lombok annotation to generate an all-arguments constructor.
 *
 * Fields:
 * {@code id} - The unique identifier for the adoption record.
 * {@code user} - The user who adopted the animal.
 * {@code animal} - The adopted animal.
 * {@code adoptionDate} - The date when the adoption took place.
 *
 * Relationships:
 * {@code @ManyToOne} - Defines a many-to-one relationship with the {@code User} entity.
 * {@code @ManyToOne} - Defines a many-to-one relationship with the {@code Animal} entity.
 */

@Entity
@Table(name = "adoptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adoption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    private LocalDateTime adoptionDate;
}
