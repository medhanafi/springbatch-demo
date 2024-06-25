package net.siinergy.springbatch.demo.jobs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    private Director director;

    private Integer year;

    private Integer duration;

    private Float rating;

    @ManyToMany(mappedBy = "movies")
    private Set<Genre> genre;

    @ManyToMany(mappedBy = "movies")
    private Set<Country> countries;
}