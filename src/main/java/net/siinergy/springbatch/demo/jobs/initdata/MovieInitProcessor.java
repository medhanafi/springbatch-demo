package net.siinergy.springbatch.demo.jobs.initdata;

import net.siinergy.springbatch.demo.dto.CountryDto;
import net.siinergy.springbatch.demo.dto.DirectorDto;
import net.siinergy.springbatch.demo.dto.GenreDto;
import net.siinergy.springbatch.demo.dto.MovieDto;
import net.siinergy.springbatch.demo.model.Movie;
import org.springframework.batch.item.ItemProcessor;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MovieInitProcessor implements ItemProcessor<Movie, MovieDto> {
    @Override
    public MovieDto process(Movie item) {

       return new MovieDto()
               .setId(Long.parseLong(item.getId()))
               .setTitle(item.getTitle())
               .setYear(Integer.parseInt(item.getYear()))
               .setGenreDto(parseGenre(item.getGenre()))
               .setDuration(parseDuration(item.getDuration()))
               .setCountries(parseCountries(item.getCountries()))
               .setDirectorDto(new DirectorDto(item.getDirector()))
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

    private Set<GenreDto> parseGenre(String value) {
        return Arrays.stream(value.split("\\|")).map(item -> new GenreDto().setLabel(item.trim())).collect(Collectors.toSet());
    }

    private Set<CountryDto> parseCountries(String value) {
        return Arrays.stream(value.split("\\|")).map(item -> new CountryDto().setName(item.trim())).collect(Collectors.toSet());
    }

    private Long parseDuration(String value) {
        return Duration.parse("PT" +
                value.replace(" ", "")
                        .replace("h", "H")
                        .replace("min", "M")
        ).toSeconds();
    }
}
