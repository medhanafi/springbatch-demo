package net.siinergy.springbatch.demo.dto;

public class DirectorDto {
    private Long id;

    private String fullName;

    public DirectorDto(String director) {
        this.fullName = director;
    }

    public Long getId() {
        return id;
    }

    public DirectorDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public DirectorDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
}
