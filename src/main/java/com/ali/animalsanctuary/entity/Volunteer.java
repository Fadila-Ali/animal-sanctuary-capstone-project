package com.ali.animalsanctuary.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


/**
 * Represents a volunteer entity in the Animal Sanctuary application.
 * Each volunteer record includes information about the task, start and end dates, and the users assigned to the task.
 * The entity is annotated for use with JPA/Hibernate.
 *
 * Annotations:
 * {@code @Entity} - Specifies that this class is an entity and is mapped to a database table.
 * {@code @Table(name = "volunteers")} - Specifies the name of the database table to be used for mapping.
 * {@code @Data} - Lombok annotation to generate boilerplate code such as getters, setters, toString, etc.
 * {@code @NoArgsConstructor} - Lombok annotation to generate a no-arguments constructor.
 * {@code @AllArgsConstructor} - Lombok annotation to generate an all-arguments constructor.
 *
 * Fields:
 * {@code id} - The unique identifier for the volunteer record.
 * {@code startDate} - The start date of the volunteer task.
 * {@code endDate} - The end date of the volunteer task.
 * {@code task} - A description of the volunteer task.
 * {@code users} - The set of users assigned to the volunteer task.
 *
 * Relationships:
 * {@code @ManyToMany} - Defines a many-to-many relationship with the {@code User} entity.
 *
 * Column Constraints:
 * {@code @NotNull(message = "Start date is required")} - Specifies that the start date cannot be null and provides a custom validation message.
 * {@code @NotNull(message = "End date is required")} - Specifies that the end date cannot be null and provides a custom validation message.
 * {@code @NotNull(message = "Task is required")} - Specifies that the task description cannot be null and provides a custom validation message.
 *
 * Join Table:
 * {@code @JoinTable(name = "volunteer_users")} - Specifies the join table for the many-to-many relationship between volunteers and users.
 * {@code @JoinColumn(name = "volunteer_id")} - Defines the join column for the volunteer side of the relationship.
 * {@code @JoinColumn(name = "user_id")} - Defines the join column for the user side of the relationship.
 */

@Entity
@Table(name = "volunteers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;

    @NotNull(message = "Task is required")
    private String task;

    @ManyToMany
    @JoinTable(
            name = "volunteer_users",
            joinColumns = @JoinColumn(name = "volunteer_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();
}
