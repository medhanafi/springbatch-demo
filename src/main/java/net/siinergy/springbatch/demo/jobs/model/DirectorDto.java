package net.siinergy.springbatch.demo.jobs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


public class DirectorDto {
    private Long id;

    private String fullName;

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
