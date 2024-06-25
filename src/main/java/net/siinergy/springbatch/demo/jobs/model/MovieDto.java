package net.siinergy.springbatch.demo.jobs.model;



import java.util.Set;


public class MovieDto {

    private Long id;

    private String title;

    private DirectorDto director;

    private Integer year;

    private Long duration;

    private Float rating;

    private Set<GenreDto> genre;

    private Set<CountryDto> countries;
    private String imdbLink;
    private String imdbId;
    private String posterUri;

    public Long getId() {
        return id;
    }

    public MovieDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MovieDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public DirectorDto getDirector() {
        return director;
    }

    public MovieDto setDirector(DirectorDto director) {
        this.director = director;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public MovieDto setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Long getDuration() {
        return duration;
    }

    public MovieDto setDuration(Long duration) {
        this.duration = duration;
        return this;
    }

    public Float getRating() {
        return rating;
    }

    public MovieDto setRating(Float rating) {
        this.rating = rating;
        return this;
    }

    public Set<GenreDto> getGenre() {
        return genre;
    }

    public MovieDto setGenre(Set<GenreDto> genre) {
        this.genre = genre;
        return this;
    }

    public Set<CountryDto> getCountries() {
        return countries;
    }

    public MovieDto setCountries(Set<CountryDto> countries) {
        this.countries = countries;
        return this;
    }

    public MovieDto setImdbLink(String imdbLink) {
        this.imdbLink=imdbLink;
        return this;
    }

    public String getImdbLink() {
        return imdbLink;
    }

    public String getImdbId() {
        return imdbId;
    }

    public MovieDto setImdbId(String imdbId) {
        this.imdbId = imdbId;
        return this;
    }

    public String getPosterUri() {
        return posterUri;
    }

    public MovieDto setPosterUri(String posterUri) {
        this.posterUri = posterUri;
        return this;
    }

}
