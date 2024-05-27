package net.siinergy.springbatch.demo.model;

public class Movie {

    private String id;

    private String title;

    private String year;

    private String genre;

    private String duration;

    private String countries;

    private String director;

    private String rating;

    private String imdbLink;

    public String getId() {
        return id;
    }

    public Movie setId(String id) {
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

    public String getYear() {
        return year;
    }

    public Movie setYear(String year) {
        this.year = year;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public Movie setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public String getDuration() {
        return duration;
    }

    public Movie setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public String getCountries() {
        return countries;
    }

    public Movie setCountries(String countries) {
        this.countries = countries;
        return this;
    }

    public String getDirector() {
        return director;
    }

    public Movie setDirector(String director) {
        this.director = director;
        return this;
    }

    public String getRating() {
        return rating;
    }

    public Movie setRating(String rating) {
        this.rating = rating;
        return this;
    }

    public String getImdbLink() {
        return imdbLink;
    }

    public Movie setImdbLink(String imdbLink) {
        this.imdbLink = imdbLink;
        return this;
    }
}
