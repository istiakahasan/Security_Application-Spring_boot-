package com.SecurityApp.SecurityApplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PostEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String tittle;

    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @PrePersist
    void beforeSave(){

    }
    @PreUpdate
    void beforeUpdate(){

    }
    @PreRemove
    void beforeDelete(){

    }
}
