package com.project.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
//hello
    @NotBlank(message="Genre code is mandatory")
    private String code;

    @NotBlank(message="Genre name is mandatory")
    private String name;

    @Size(max=500,message="description must not exceed 500 characters")
    private String description;
}
