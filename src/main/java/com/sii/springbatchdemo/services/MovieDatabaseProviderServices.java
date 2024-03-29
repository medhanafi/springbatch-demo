package com.sii.springbatchdemo.services;

import com.sii.springbatchdemo.entity.Country;
import com.sii.springbatchdemo.entity.Director;
import com.sii.springbatchdemo.entity.Genre;
import com.sii.springbatchdemo.entity.Movie;
import com.sii.springbatchdemo.model.CountryDto;
import com.sii.springbatchdemo.model.DirectorDto;
import com.sii.springbatchdemo.model.GenreDto;
import com.sii.springbatchdemo.model.MovieDto;
import com.sii.springbatchdemo.repository.MovieRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieDatabaseProviderServices {

    private final MovieRepository movieRepository;
    private final EntityManager entityManager;

    @Transactional
    public void run() throws Exception {

        final var movies = parseCsv();

        //List countries
        final HashSet<String> countries = new HashSet<>();
        final HashSet<String> genres = new HashSet<>();
        final HashSet<String> directors = new HashSet<>();

        movies.forEach(m -> {
            countries.addAll(m.getCountries().stream().map(CountryDto::getName).toList());
            genres.addAll(m.getGenre().stream().map(GenreDto::getLabel).toList());
            directors.add(m.getDirector().getFullName());
        });
        log.info("List of countries : {} ", countries);
        log.info("List of genres : {} ", genres);
        log.info("List of directors : {} ", directors);

        //Creating countries
        final HashMap<String, Country> countryMap = new HashMap<>();

        countries.forEach(c -> {
            final var country = new Country();
            country.setName(c);
            country.setMovies(new HashSet<>());
            countryMap.put(c, country);
            entityManager.persist(country);
        });

        //Creating genres
        final HashMap<String, Genre> genreMap = new HashMap<>();

        genres.forEach(g -> {
            final var genre = new Genre();
            genre.setLabel(g);
            genre.setMovies(new HashSet<>());
            genreMap.put(g, genre);
            entityManager.persist(genre);
        });

        //Saving directors
        final HashMap<String, Director> directorMap = new HashMap<>();

        directors.forEach(d -> {
            final var director = new Director();
            director.setFullName(d);
            director.setMovies(new HashSet<>());
            directorMap.put(d, director);
            entityManager.persist(director);

        });

        movies.forEach(m -> {
            final var movie = new Movie();
            movie.setCountries(m.getCountries().stream().map(c -> {
                final var item = countryMap.get(c.getName());
                item.getMovies().add(movie);
                return item;
            }).collect(Collectors.toSet()));
            movie.setDuration(m.getDuration());
            final var director = directorMap.get(m.getDirector().getFullName());
            director.getMovies().add(movie);
            movie.setDirector(director);
            movie.setRating(m.getRating());
            movie.setYear(m.getYear());
            movie.setGenre(m.getGenre().stream().map(c -> {
                final var item = genreMap.get(c.getLabel());
                item.getMovies().add(movie);
                return item;
            }).collect(Collectors.toSet()));
            movie.setTitle(m.getTitle());
            movieRepository.save(movie);
        });

    }

    public List<MovieDto> parseCsv() throws Exception {
        final var ret = new ArrayList<MovieDto>();

        try (final var is = new ClassPathResource("movies.csv").getInputStream()) {

            final var csv = CSVParser.parse(is, StandardCharsets.UTF_8, CSVFormat.DEFAULT);
            final var iterator = csv.stream().iterator();

            // Skip the header line
            iterator.next();

            while (iterator.hasNext()) {
                final var line = iterator.next();
                try {
                    // Split the line into array of values
                    final var movie = MovieDto.builder();
                    movie.title(line.get(1));
                    movie.year(Integer.parseInt(line.get(2)));
                    movie.duration(parseDuration(line.get(4)).intValue());
                    movie.director(DirectorDto.builder().fullName(line.get(6)).build());
                    movie.genre(parseGenre(line.get(3)));
                    movie.countries(parseCountries(line.get(5)));
                    movie.rating(Float.parseFloat(line.get(7)));
                    ret.add(movie.build());
                } catch (Exception e) {
                    log.error("Error while parsing line '{}'", line, e);
                }
            }
        }
        return ret;
    }

    private Set<GenreDto> parseGenre(String value) {
        return Arrays.stream(value.split("\\|")).map(item -> GenreDto.builder().label(item.trim()).build()).collect(Collectors.toSet());
    }

    private Set<CountryDto> parseCountries(String value) {
        return Arrays.stream(value.split("\\|")).map(item -> CountryDto.builder().name(item.trim()).build()).collect(Collectors.toSet());
    }

    private Long parseDuration(String value) {
        return Duration.parse("PT" +
            value.replace(" ", "")
                .replace("h", "H")
                .replace("min", "M")
        ).toSeconds();
    }
}
