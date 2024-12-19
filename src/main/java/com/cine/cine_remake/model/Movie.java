package com.cine.cine_remake.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cine.cine_remake.model.enums.CinemaStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "duration_minutes", nullable = false)
    private int durationMinutes;

    @Column(name = "rating", nullable = false)
    private double rating;

    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String synopsis;

    @Column(name = "original_language", nullable = false)
    private String originalLanguage;
    
    @Column(name = "age_rating", nullable = false)
    private String ageRating;

    @Column(name = "poster_url")
    private String posterUrl;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estate", nullable = false)
    private CinemaStatus estate;
    

    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    @Column(name = "lasted_update", nullable = false)
    private LocalDateTime lastedUpdate;

    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now();
        lastedUpdate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastedUpdate = LocalDateTime.now();
    }
}
