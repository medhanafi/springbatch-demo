package net.siinergy.springbatch.demo.jobs.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;


public class CountryDto {
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public CountryDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CountryDto setName(String name) {
        this.name = name;
        return this;
    }
}
