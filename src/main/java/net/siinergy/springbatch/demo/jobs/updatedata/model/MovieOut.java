package net.siinergy.springbatch.demo.jobs.updatedata.model;


public class MovieOut {
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
    private String snapshotUri;

    public String getId() {
        return id;
    }

    public MovieOut setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MovieOut setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getYear() {
        return year;
    }

    public MovieOut setYear(String year) {
        this.year = year;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public MovieOut setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public String getDuration() {
        return duration;
    }

    public MovieOut setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public MovieOut setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public String getDirector() {
        return director;
    }

    public MovieOut setDirector(String director) {
        this.director = director;
        return this;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public MovieOut setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
        return this;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public MovieOut setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
        return this;
    }

    public String getImdbLink() {
        return imdbLink;
    }

    public MovieOut setImdbLink(String imdbLink) {
        this.imdbLink = imdbLink;
        return this;
    }

    public String getSnapshotUri() {
        return snapshotUri;
    }

    public MovieOut setSnapshotUri(String snapshotUri) {
        this.snapshotUri = snapshotUri;
        return this;
    }
}
