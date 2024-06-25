package net.siinergy.springbatch.demo.jobs.batch.initdata;

import net.siinergy.springbatch.demo.jobs.entity.Country;
import net.siinergy.springbatch.demo.jobs.entity.Director;
import net.siinergy.springbatch.demo.jobs.entity.Genre;
import net.siinergy.springbatch.demo.jobs.entity.Movie;
import net.siinergy.springbatch.demo.jobs.model.CountryDto;
import net.siinergy.springbatch.demo.jobs.model.GenreDto;
import net.siinergy.springbatch.demo.jobs.model.MovieDto;
import net.siinergy.springbatch.demo.jobs.repository.MovieRepository;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class MovieInitWriter implements ItemWriter<MovieDto> {
  @Autowired
    private  MovieRepository movieRepository;
    @Autowired
    private  EntityManager entityManager;

    @Override
    public void write(@NonNull Chunk<? extends MovieDto> movies) {
        //List countries
        final HashSet<String> countries = new HashSet<>();
        final HashSet<String> genres = new HashSet<>();
        final HashSet<String> directors = new HashSet<>();

        movies.forEach(m -> {
            countries.addAll(m.getCountries().stream().map(CountryDto::getName).toList());
            genres.addAll(m.getGenre().stream().map(GenreDto::getLabel).toList());
            directors.add(m.getDirector().getFullName());
        });

        //Creating countries
        final HashMap<String, Country> countryMap = new HashMap<>();


        countries.forEach(c -> {
            int i=0;
            final var country = new Country(++i);
            country.setName(c);
            country.setMovies(new HashSet<>());
            countryMap.put(c, country);
            entityManager.merge(country);
        });

        //Creating genres
        final HashMap<String, Genre> genreMap = new HashMap<>();

        genres.forEach(g -> {
            final var genre = new Genre();
            genre.setLabel(g);
            genre.setMovies(new HashSet<>());
            genreMap.put(g, genre);
            entityManager.merge(genre);
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
            movie.setDuration(m.getDuration().intValue());
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

}
