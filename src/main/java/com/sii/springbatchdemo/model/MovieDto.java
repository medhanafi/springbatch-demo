package com.sii.springbatchdemo.model;

import com.sii.springbatchdemo.entity.Country;
import com.sii.springbatchdemo.entity.Director;
import com.sii.springbatchdemo.entity.Genre;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class MovieDto {

    private Long id;

    private String title;

    private DirectorDto director;

    private Integer year;

    private Integer duration;

    private Float rating;

    private Set<GenreDto> genre;

    private Set<CountryDto> countries;
}
