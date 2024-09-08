package net.siinergy.springbatch.demo.jobs.batch.initdata;

import net.siinergy.springbatch.demo.jobs.model.CountryDto;
import net.siinergy.springbatch.demo.jobs.model.GenreDto;
import net.siinergy.springbatch.demo.jobs.model.MovieDto;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;

@Component
public class MovieInitWriter implements ItemWriter<MovieDto> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void write(@NonNull Chunk<? extends MovieDto> movies) {
        // List countries, genres, and directors
        final HashSet<String> countries = new HashSet<>();
        final HashSet<String> genres = new HashSet<>();
        final HashSet<String> directors = new HashSet<>();

        movies.forEach(m -> {
            countries.addAll(m.getCountries().stream().map(CountryDto::getName).toList());
            genres.addAll(m.getGenre().stream().map(GenreDto::getLabel).toList());
            directors.add(m.getDirector().getFullName());
        });

        // Creating countries
        final HashMap<String, Long> countryIdMap = new HashMap<>(); // Store IDs

        countries.forEach(c -> {
            jdbcTemplate.update("INSERT INTO country (name) VALUES (?)", c);
            Long countryId = jdbcTemplate.queryForObject("SELECT id FROM country WHERE name = ?", Long.class, c);
            countryIdMap.put(c, countryId);
        });

        // Creating genres
        final HashMap<String, Long> genreIdMap = new HashMap<>(); // Store IDs

        genres.forEach(g -> {
            jdbcTemplate.update("INSERT INTO genre (label) VALUES (?)", g);
            Long genreId = jdbcTemplate.queryForObject("SELECT id FROM genre WHERE label = ?", Long.class, g);
            genreIdMap.put(g, genreId);
        });

        // Saving directors
        final HashMap<String, Long> directorIdMap = new HashMap<>(); // Store IDs

        directors.forEach(d -> {
            jdbcTemplate.update("INSERT INTO director (full_name) VALUES (?)", d);
            Long directorId = jdbcTemplate.queryForObject("SELECT id FROM director WHERE full_name = ?", Long.class, d);
            directorIdMap.put(d, directorId);
        });

        movies.forEach(m -> {
            // Insert movie into DB and get the generated ID
            jdbcTemplate.update("INSERT INTO movie (title, duration, rating, year, director_id) VALUES (?, ?, ?, ?, ?)",
                    m.getTitle(), m.getDuration().intValue(), m.getRating(), m.getYear(), directorIdMap.get(m.getDirector().getFullName()));
            Long movieId = jdbcTemplate.queryForObject("SELECT id FROM movie WHERE title = ?", Long.class, m.getTitle());

            // Insert into movie-country relation
            m.getCountries().forEach(c -> {
                Long countryId = countryIdMap.get(c.getName());
                if (countryId != null) {
                    jdbcTemplate.update("INSERT INTO country_movies (movie_id, country_id) VALUES (?, ?)", movieId, countryId);
                }
            });

            // Insert into movie-genre relation
            m.getGenre().forEach(g -> {
                Long genreId = genreIdMap.get(g.getLabel());
                if (genreId != null) {
                    jdbcTemplate.update("INSERT INTO genre_movies (movie_id, genre_id) VALUES (?, ?)", movieId, genreId);
                }
            });

            // Insert into movie-director relation (if applicable)
            Long directorId = directorIdMap.get(m.getDirector().getFullName());
            if (directorId != null) {
                jdbcTemplate.update("INSERT INTO director_movies (movie_id, director_id) VALUES (?, ?)", movieId, directorId);
            }

            System.out.println("Saved movie: " + m.getTitle());
        });
    }
}
