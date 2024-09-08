package net.siinergy.springbatch.demo.jobs.model;



import java.util.Set;


public class Movie {

    private Long id;

    private String title;

    private Director director;

    private Integer year;

    private Long duration;

    private Float rating;

    private Set<Genre> genre;

    private Set<Country> countries;
    private String imdbLink;
    private String imdbId;
    private String posterUri;

    public Long getId() {
        return id;
    }

    public Movie setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public Director getDirector() {
        return director;
    }

    public Movie setDirector(Director director) {
        this.director = director;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public Movie setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Long getDuration() {
        return duration;
    }

    public Movie setDuration(Long duration) {
        this.duration = duration;
        return this;
    }

    public Float getRating() {
        return rating;
    }

    public Movie setRating(Float rating) {
        this.rating = rating;
        return this;
    }

    public Set<Genre> getGenre() {
        return genre;
    }

    public Movie setGenre(Set<Genre> genre) {
        this.genre = genre;
        return this;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public Movie setCountries(Set<Country> countries) {
        this.countries = countries;
        return this;
    }

    public Movie setImdbLink(String imdbLink) {
        this.imdbLink=imdbLink;
        return this;
    }

    public String getImdbLink() {
        return imdbLink;
    }

    public String getImdbId() {
        return imdbId;
    }

    public Movie setImdbId(String imdbId) {
        this.imdbId = imdbId;
        return this;
    }

    public String getPosterUri() {
        return posterUri;
    }

    public Movie setPosterUri(String posterUri) {
        this.posterUri = posterUri;
        return this;
    }

}
