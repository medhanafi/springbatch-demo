package net.siinergy.springbatch.demo.jobs.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Set;


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
