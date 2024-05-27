package net.siinergy.springbatch.demo.jobs.updatedata.model;


public class MovieIn {
    private String id;
    private String title;
    private String year;
    private String origin;
    private String duration;
    private String genre;
    private String director;
    private String imdbRating;
    private String ratingCount;
    private String imdbLink;

    public String getId() {
        return id;
    }

    public MovieIn setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MovieIn setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getYear() {
        return year;
    }

    public MovieIn setYear(String year) {
        this.year = year;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public MovieIn setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public String getDuration() {
        return duration;
    }

    public MovieIn setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public MovieIn setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public String getDirector() {
        return director;
    }

    public MovieIn setDirector(String director) {
        this.director = director;
        return this;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public MovieIn setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
        return this;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public MovieIn setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
        return this;
    }

    public String getImdbLink() {
        return imdbLink;
    }

    public MovieIn setImdbLink(String imdbLink) {
        this.imdbLink = imdbLink;
        return this;
    }
}
