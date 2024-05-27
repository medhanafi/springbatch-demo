package net.siinergy.springbatch.demo.jobs.updatedata;

import net.siinergy.springbatch.demo.dto.CountryDto;
import net.siinergy.springbatch.demo.dto.DirectorDto;
import net.siinergy.springbatch.demo.dto.GenreDto;
import net.siinergy.springbatch.demo.dto.MovieDto;
import net.siinergy.springbatch.demo.model.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MovieUpdateProcessor implements ItemProcessor<Movie, MovieDto> {

    String default404 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1GOANWIDig4XmCjeo0bZGqHu9Fk3Q708WfQ&s";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MovieUpdateProcessor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public MovieDto process(Movie item) throws Exception {
        String imdbLink = item.getImdbLink();
        String snapshotUri = fetchSnapshotUri(imdbLink);

        // Create MovieOut object and set all properties from MovieIn plus snapshotUri
        return new MovieDto()
                .setId(Long.parseLong(item.getId()))
                .setTitle(item.getTitle())
                .setYear(Integer.parseInt(item.getYear()))
                .setGenreDto(getGenresByName(item.getGenre()))
                .setDuration(parseDuration(item.getDuration()))
                .setCountries(getCountriesBy(item.getCountries()))
                .setDirectorDto(new DirectorDto(item.getDirector()))
                .setRating(Float.parseFloat(item.getRating()))
                .setImdbLink(item.getImdbLink())
                .setImdbId(parseImdbId(item.getImdbLink()))
                .setMoviePosterUri(snapshotUri);
    }


    private String fetchSnapshotUri(String imdbLink) {
        try {
            if ("IMDB link".equals(imdbLink))
                return "snapshot Uri";
            // Extract IMDb ID from the provided IMDb link
            String imdbId = parseImdbId(imdbLink);
            if (imdbId == null) {
                return null;
            }

            // Reconstruct the IMDb URL with the title ID to fetch the snapshot
            String reconstructedUrl = "https://www.imdb.com/title/" + imdbId + "/mediaindex/?ref_=tt_mv_sm";

            Document document = Jsoup.connect(reconstructedUrl)
                    .proxy("localhost", 3128)
                    .get();
            Elements posterElements = document.select(".poster");
            if (!posterElements.isEmpty()) {
                Element posterElement = posterElements.first();
                return Objects.requireNonNull(posterElement).attr("src");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return default404;
    }


    private Set<GenreDto> getGenresByName(String value) {

        String sql = "SELECT * FROM genres WHERE label IN (:names)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("names", String.join(",", value.split("\\|")));
        List<GenreDto> result = jdbcTemplate.query(sql, parameters, (rs, rowNum) -> new GenreDto()
                .setId(rs.getLong("id"))
                .setLabel(rs.getString("label")));
        return new HashSet<>(result);
    }

    private Set<CountryDto> getCountriesBy(String value) {
        String joinedNames = Arrays.stream(value.split("\\|"))
                .map(name -> "'" + name.replace("'", "''") + "'")
                .collect(Collectors.joining(","));
        List<CountryDto> result = jdbcTemplate.query(String.format("SELECT * FROM countries WHERE name IN (%s)", joinedNames),
                (rs, rowNum) -> new CountryDto()
                        .setId(rs.getLong("id"))
                        .setName(rs.getString("name")));
        return new HashSet<>(result);
    }

    private Long parseDuration(String value) {
        return Duration.parse("PT" +
                value.replace(" ", "")
                        .replace("h", "H")
                        .replace("min", "M")
        ).toSeconds();
    }

    private final Pattern pattern = Pattern.compile("(tt\\d{7})");

    private String parseImdbId(String url) {
        Matcher matcher = pattern.matcher(url);
        if (matcher.find() && matcher.groupCount() == 1)
            return matcher.group(0);
        return "";
    }
}
