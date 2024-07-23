package com.ali.animalsanctuary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a visit entity in the Animal Sanctuary application.
 * Each visit records the user who scheduled the visit, the start and end times, and the availability status.
 * The entity is annotated for use with JPA/Hibernate.
 *
 * Annotations:
 * {@code @Entity} - Specifies that this class is an entity and is mapped to a database table.
 * {@code @Table(name = "visits")} - Specifies the name of the database table to be used for mapping.
 * {@code @Data} - Lombok annotation to generate boilerplate code such as getters, setters, toString, etc.
 * {@code @NoArgsConstructor} - Lombok annotation to generate a no-arguments constructor.
 * {@code @AllArgsConstructor} - Lombok annotation to generate an all-arguments constructor.
 *
 * Fields:
 * {@code id} - The unique identifier for the visit record.
 * {@code startTime} - The start time of the visit.
 * {@code endTime} - The end time of the visit.
 * {@code user} - The user who scheduled the visit.
 * {@code available} - The availability status of the visit slot.
 *
 * Relationships:
 * {@code @ManyToOne(fetch = FetchType.LAZY)} - Defines a many-to-one relationship with the {@code User} entity, with lazy fetching.
 *
 * Column Constraints:
 * {@code @Column(nullable = false)} - Specifies that the column cannot be null.
 */

@Entity
@Table(name = "visits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private boolean available = true;

}
