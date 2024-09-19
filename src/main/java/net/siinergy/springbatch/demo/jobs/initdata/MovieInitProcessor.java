package net.siinergy.springbatch.demo.jobs.initdata;


import net.siinergy.springbatch.demo.model.MovieData;
import net.siinergy.springbatch.demo.model.Country;
import net.siinergy.springbatch.demo.model.Director;
import net.siinergy.springbatch.demo.model.Genre;
import net.siinergy.springbatch.demo.model.Movie;
import org.springframework.batch.item.ItemProcessor;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MovieInitProcessor implements ItemProcessor<MovieData, Movie> {
    @Override
    public Movie process(MovieData item) {

       return new Movie()
               .setId(Long.parseLong(item.getId()))
               .setTitle(item.getTitle())
               .setYear(Integer.parseInt(item.getYear()))
               .setGenre(parseGenre(item.getGenre()))
               .setDuration(parseDuration(item.getDuration()))
               .setCountries(parseCountries(item.getCountries()))
               .setDirector(new Director().setFullName(item.getDirector()))
               .setRating(Float.parseFloat(item.getRating()))
               .setImdbLink(item.getImdbLink())
               .setImdbId(parseImdbId(item.getImdbLink()));
    }

    private final Pattern pattern = Pattern.compile("(tt\\d{7})");

    private String parseImdbId(String url) {
        Matcher matcher = pattern.matcher(url);
        if (matcher.find() && matcher.groupCount() == 1)
            return matcher.group(0);
        return "";
    }

    private Set<Genre> parseGenre(String value) {
        return Arrays.stream(value.split("\\|")).map(item -> new Genre().setLabel(item.trim())).collect(Collectors.toSet());
    }

    private Set<Country> parseCountries(String value) {
        return Arrays.stream(value.split("\\|")).map(item -> new Country().setName(item.trim())).collect(Collectors.toSet());
    }

    private Long parseDuration(String value) {
        return Duration.parse("PT" +
                value.replace(" ", "")
                        .replace("h", "H")
                        .replace("min", "M")
        ).toSeconds();
    }
}
