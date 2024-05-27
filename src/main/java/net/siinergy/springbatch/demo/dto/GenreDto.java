package net.siinergy.springbatch.demo.dto;


public class GenreDto {
    private Long id;

    private String label;

    public Long getId() {
        return id;
    }

    public GenreDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public GenreDto setLabel(String label) {
        this.label = label;
        return this;
    }
}
