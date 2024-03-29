package com.sii.springbatchdemo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class GenreDto {
    private Long id;

    private String label;
}
