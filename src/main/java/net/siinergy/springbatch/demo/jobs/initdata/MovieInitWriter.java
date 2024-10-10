package net.siinergy.springbatch.demo.jobs.initdata;

import net.siinergy.springbatch.demo.model.Country;
import net.siinergy.springbatch.demo.model.Genre;
import net.siinergy.springbatch.demo.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;

@Component
public class MovieInitWriter implements ItemWriter<Movie> {

    private static final Logger LOG = LoggerFactory.getLogger(MovieInitWriter.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void write(@NonNull Chunk<? extends Movie> movies) {
        // Créer des ensembles pour stocker les pays, genres et réalisateurs
        HashSet<String> countries = new HashSet<>();
        HashSet<String> genres = new HashSet<>();
        HashSet<String> directors = new HashSet<>();

        // Collecter les pays, genres et réalisateurs
        for (Movie movie : movies) {
            for (Country country : movie.getCountries()) {
                countries.add(country.getName());
            }
            for (Genre genre : movie.getGenre()) {
                genres.add(genre.getLabel());
            }
            directors.add(movie.getDirector().getFullName());
        }

        // Créer des pays dans la base de données et récupérer leurs IDs
        HashMap<String, Long> countryIdMap = createEntities(countries, "country", "country_name");

        // Créer des genres dans la base de données et récupérer leurs IDs
        HashMap<String, Long> genreIdMap = createEntities(genres, "genre", "genre_label");

        // Créer des réalisateurs dans la base de données et récupérer leurs IDs
        HashMap<String, Long> directorIdMap = createEntities(directors, "director", "full_name");

        // Traiter chaque film pour l'insertion dans la base de données
        for (Movie movie : movies) {
            // Vérifier si le film existe déjà dans la base de données
            Long movieId = getIdByColumn("movie", "movie_title", movie.getTitle());
            if (movieId == null) {
                // Si le film n'existe pas, l'insérer dans la table movie
                jdbcTemplate.update("INSERT INTO movie (movie_title, duration, rating, movie_year) VALUES (?, ?, ?, ?)",
                        movie.getTitle(), movie.getDuration().intValue(), movie.getRating(), movie.getYear());
                LOG.info("Film ajouté : {}", movie.getTitle());
                // Récupérer à nouveau l'ID du film
                movieId = getIdByColumn("movie", "movie_title", movie.getTitle());
            } else {
                LOG.info("Film déjà présent : {}", movie.getTitle());
            }

            // Insérer les relations film-pays
            insertRelations(movie.getCountries(), movieId, countryIdMap, "country_movies", "movie_id", "country_id");

            // Insérer les relations film-genre
            insertRelations(movie.getGenre(), movieId, genreIdMap, "genre_movies", "movie_id", "genre_id");

            // Insérer la relation film-réalisateur si applicable
            Long directorId = directorIdMap.get(movie.getDirector().getFullName());
            if (directorId != null) {
                jdbcTemplate.update(
                        "INSERT INTO director_movies (movie_id, director_id) " +
                                "SELECT ?, ? " +
                                "WHERE NOT EXISTS (" +
                                "SELECT 1 FROM director_movies WHERE movie_id = ? AND director_id = ?)",
                        movieId, directorId, movieId, directorId
                );

                LOG.info("Relation film-réalisateur ajoutée pour : {}", movie.getTitle());
            } else {
                LOG.warn("Réalisateur non trouvé pour le film : {}", movie.getTitle());
            }
        }
    }

    // Méthode pour créer des entités et retourner une carte avec leurs IDs
    private HashMap<String, Long> createEntities(HashSet<String> entities, String tableName, String columnName) {
        HashMap<String, Long> idMap = new HashMap<>();
        for (String entity : entities) {
            // Récupérer l'ID de l'entité
            Long entityId = getIdByColumn(tableName, columnName, entity);
            if (entityId == null) {
                // Si l'entité n'existe pas, l'insérer dans la base de données
                jdbcTemplate.update(String.format("INSERT INTO %s (%s) VALUES (?)", tableName, columnName), entity);
                LOG.info("Entité ajoutée : {} dans {}", entity, tableName);
                // Récupérer à nouveau l'ID de l'entité
                entityId = getIdByColumn(tableName, columnName, entity);
            }
            // Ajouter l'entité et son ID à la carte
            idMap.put(entity, entityId);
        }
        return idMap;
    }

    // Méthode pour obtenir un ID en fonction d'une colonne
    private Long getIdByColumn(String tableName, String columnName, String value) {
        try {
            // Exécuter la requête pour obtenir l'ID
            return jdbcTemplate.queryForObject(String.format("SELECT id FROM %s WHERE %s = ?", tableName, columnName), Long.class, value);
        } catch (EmptyResultDataAccessException e) {
            // Loguer un message si l'entité n'existe pas
            LOG.warn("{} n'existe pas encore dans {}", value, tableName);
            return null;
        }
    }

    // Méthode pour insérer des relations dans la base de données
    private <T> void insertRelations(Iterable<T> items, Long movieId, HashMap<String, Long> idMap, String tableName, String movieColumn, String entityColumn) {
        for (T item : items) {
            String entityName;
            if (item instanceof Country) {
                entityName = ((Country) item).getName();
            } else if (item instanceof Genre) {
                entityName = ((Genre) item).getLabel();
            } else {
                continue; // Passer si ce n'est pas un type reconnu
            }

            // Récupérer l'ID de l'entité
            Long entityId = idMap.get(entityName);
            if (entityId != null) {
                String sql=String.format("INSERT INTO %s (%s, %s) SELECT ?, ? WHERE NOT EXISTS (SELECT 1 FROM %s WHERE %s = ? AND %s = ?)",
                        tableName, movieColumn, entityColumn, tableName, movieColumn, entityColumn);
                // Insérer la relation dans la base de données
                jdbcTemplate.update(sql, movieId, entityId, movieId, entityId);
                LOG.info("Relation ajoutée : Film ID = {}, Entité ID = {}", movieId, entityId);
            } else {
                LOG.warn("ID non trouvé pour l'entité : {}", entityName);
            }
        }
    }
}
