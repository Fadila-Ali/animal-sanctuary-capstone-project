package com.ali.animalsanctuary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a role entity in the Animal Sanctuary application.
 * Each role has a unique name and can be associated with multiple users.
 * The entity is annotated for use with JPA/Hibernate.
 *
 * Annotations:
 * {@code @Entity} - Specifies that this class is an entity and is mapped to a database table.
 * {@code @Table(name = "roles")} - Specifies the name of the database table to be used for mapping.
 * {@code @Data} - Lombok annotation to generate boilerplate code such as getters, setters, toString, etc.
 * {@code @NoArgsConstructor} - Lombok annotation to generate a no-arguments constructor.
 * {@code @AllArgsConstructor} - Lombok annotation to generate an all-arguments constructor.
 *
 * Fields:
 * {@code id} - The unique identifier for the role.
 * {@code name} - The unique name of the role.
 * {@code users} - The list of users associated with the role.
 *
 * Relationships:
 * {@code @ManyToMany} - Defines a many-to-many relationship with the {@code User} entity.
 */

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;


}
