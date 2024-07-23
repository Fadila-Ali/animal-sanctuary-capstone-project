package com.ali.animalsanctuary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Represents a user entity in the Animal Sanctuary application.
 * Each user has a unique username and email, and can have multiple roles,
 * volunteer tasks, and visits associated with them.
 * The entity is annotated for use with JPA/Hibernate.
 *
 * Annotations:
 * {@code @Entity} - Specifies that this class is an entity and is mapped to a database table.
 * {@code @Table(name = "users")} - Specifies the name of the database table to be used for mapping.
 * {@code @Data} - Lombok annotation to generate boilerplate code such as getters, setters, toString, etc.
 * {@code @NoArgsConstructor} - Lombok annotation to generate a no-arguments constructor.
 * {@code @AllArgsConstructor} - Lombok annotation to generate an all-arguments constructor.
 * {@code @CreationTimestamp} - Hibernate annotation to automatically set the creation timestamp.
 * {@code @UpdateTimestamp} - Hibernate annotation to automatically set the update timestamp.
 *
 * Fields:
 * {@code id} - The unique identifier for the user.
 * {@code username} - The unique username for the user.
 * {@code email} - The unique email for the user.
 * {@code password} - The password for the user.
 * {@code firstName} - The first name of the user.
 * {@code lastName} - The last name of the user.
 * {@code roles} - The roles assigned to the user.
 * {@code volunteerTasks} - The volunteer tasks associated with the user.
 * {@code visits} - The visits associated with the user.
 * {@code createdAt} - The timestamp when the user was created.
 * {@code updatedAt} - The timestamp when the user was last updated.
 *
 * Methods:
 * {@code onCreate()} - Sets the creation timestamp before persisting.
 * {@code onUpdate()} - Sets the update timestamp before updating.
 *
 * Relationships:
 * {@code @ManyToMany} - Defines a many-to-many relationship with the {@code Role} and {@code Volunteer} entities.
 * {@code @OneToMany} - Defines a one-to-many relationship with the {@code Visit} entity.
 */

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")}
    )
    private List<Role> roles = new ArrayList<>();


    @ManyToMany(mappedBy = "users")
    private Set<Volunteer> volunteerTasks = new HashSet<>();


    @OneToMany(mappedBy = "user")
    private Set<Visit> visits = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //    @Lob
//    @Basic(fetch = FetchType.LAZY)
//    @Column(name = "profile_image", columnDefinition = "LONGBLOB")
//    private byte[] profile_img;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
