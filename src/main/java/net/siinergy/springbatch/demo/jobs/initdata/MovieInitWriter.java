package net.siinergy.springbatch.demo.jobs.initdata;

import lombok.NonNull;
import net.siinergy.springbatch.demo.dto.MovieDto;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class MovieInitWriter implements ItemWriter<MovieDto> {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MovieInitWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public void write(@NonNull Chunk<? extends MovieDto> chunk) throws Exception {
        writeDirector(chunk);

        writeGenre(chunk);

        writeCountries(chunk);

        writeMovies(chunk);
    }

    private void writeMovies(Chunk<? extends MovieDto> chunk) {
        // Insert Movies
        String movieSql = "INSERT INTO movies (id, title, director_id, year, duration, rating,  imdb_id, movie_poster_uri) " +
                "VALUES (:id, :title, :directorId, :year, :duration, :rating, :imdbId, :moviePosterUri) " +
                "ON CONFLICT (id) DO NOTHING";
        jdbcTemplate.batchUpdate(movieSql, chunk.getItems().stream()
                .map(movie -> new MapSqlParameterSource()
                        .addValue("id", movie.getId())
                        .addValue("title", movie.getTitle())
                        .addValue("directorId", movie.getDirectorDto().getId())
                        .addValue("year", movie.getYear())
                        .addValue("duration", movie.getDuration())
                        .addValue("rating", movie.getRating())
                        .addValue("imdbId", movie.getImdbId())
                        .addValue("moviePosterUri", movie.getMoviePosterUri())).toArray(SqlParameterSource[]::new));

    }

    private void writeCountries(Chunk<? extends MovieDto> chunk) {
        // Insert Countries
        String countrySql = "INSERT INTO countries ( name) VALUES ( :name) " +
                "ON CONFLICT (id) DO NOTHING";
        jdbcTemplate.batchUpdate(countrySql, chunk.getItems().stream()
                .flatMap(movie -> movie.getCountries().stream())
                .distinct()
                .map(country -> new MapSqlParameterSource()
                        .addValue("name", country.getName())).toArray(SqlParameterSource[]::new));
    }

    private void writeGenre(Chunk<? extends MovieDto> chunk) {
        // Insert Genres
        String genreSql = "INSERT INTO genres ( label) VALUES (:label) " +
                "ON CONFLICT (id) DO NOTHING";
        jdbcTemplate.batchUpdate(genreSql, chunk.getItems().stream()
                .flatMap(movie -> movie.getGenreDto().stream())
                .distinct()
                .map(genre -> new MapSqlParameterSource()
                        .addValue("label", genre.getLabel())).toArray(SqlParameterSource[]::new));
    }

    private void writeDirector(Chunk<? extends MovieDto> chunk) {
        String directorSql = "INSERT INTO directors (full_name) VALUES (:fullName) " +
                "ON CONFLICT (id) DO NOTHING";
        jdbcTemplate.batchUpdate(directorSql, chunk.getItems().stream()
                .map(MovieDto::getDirectorDto)
                .distinct()
                .map(director -> new MapSqlParameterSource()
                        .addValue("fullName", director.getFullName())).toArray(SqlParameterSource[]::new));
    }
}
