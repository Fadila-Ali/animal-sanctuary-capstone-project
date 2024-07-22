package com.ali.animalsanctuary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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

}
