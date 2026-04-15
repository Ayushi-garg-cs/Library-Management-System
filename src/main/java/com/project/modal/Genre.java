package com.project.modal;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotBlank(message="Genre code is mandatory")
    private String code;

    @NotBlank(message="Genre name is mandatory")
    private String name;

    @Size(max=500,message="description must not exceed 500 characters")
    private String description;

    @Min(value=0, message="display order cannot be negative")
    private Integer displayOrder=0;

    @Column(nullable=false)
    private boolean active=true;

    @ManyToOne
    private Genre parentGenre;

    @OneToMany
    private List<Genre> subGenres=new ArrayList<Genre>();

//    @OneToMany(mappedBy="genre", cascade=CascadeType.PERSIST)
//    private List<Book> books=new ArrayList<Book>();

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;
}
