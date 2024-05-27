package net.siinergy.springbatch.demo.jobs.updatedata;

import net.siinergy.springbatch.demo.dto.MovieDto;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class MovieUpdateWriter implements ItemWriter<MovieDto>{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public MovieUpdateWriter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }


    @Override
    public void write(Chunk<? extends MovieDto> chunk) throws Exception {
        writeMoviesGenre(chunk);

        writeMoviesCountries(chunk);

     //   writeMoviesDirector(chunk);
    }


    private void writeMoviesCountries(Chunk<? extends MovieDto> chunk) {
        // Insert Movie Countries
        String movieCountrySql = "INSERT INTO movie_countries (movie_id, country_id) VALUES (:movieId, :countryId) " +
                "ON CONFLICT (movie_id, country_id) DO NOTHING";
        jdbcTemplate.batchUpdate(movieCountrySql, chunk.getItems().stream()
                .flatMap(movie -> movie.getCountries().stream()
                        .map(country -> new MapSqlParameterSource()
                                .addValue("movieId", movie.getId())
                                .addValue("countryId", country.getId()))).toArray(SqlParameterSource[]::new));
    }

    private void writeMoviesGenre(Chunk<? extends MovieDto> chunk) {
        // Insert Movie Genres
        String movieGenreSql = "INSERT INTO movie_genres (movie_id, genre_id) VALUES (:movieId, :genreId) " +
                "ON CONFLICT (movie_id, genre_id) DO NOTHING";
        jdbcTemplate.batchUpdate(movieGenreSql, chunk.getItems().stream()
                .flatMap(movie -> movie.getGenreDto().stream()
                        .map(genre -> new MapSqlParameterSource()
                                .addValue("movieId", movie.getId())
                                .addValue("genreId", genre.getId()))).toArray(SqlParameterSource[]::new));

    }

    private void writeMoviesDirector(Chunk<? extends MovieDto> chunk) {
        // Insert Movie Genres
        String movieGenreSql = "INSERT INTO director_movies (director_id, movie_id) VALUES (:movieId, :directorId) " +
                "ON CONFLICT (director_id, movie_id) DO NOTHING";
        jdbcTemplate.batchUpdate(movieGenreSql, chunk.getItems().stream()
                .flatMap(movie -> movie.getGenreDto().stream()
                        .map(genre -> new MapSqlParameterSource()
                                .addValue("movieId", movie.getId())
                                .addValue("directorId", genre.getId()))).toArray(SqlParameterSource[]::new));

    }

}
