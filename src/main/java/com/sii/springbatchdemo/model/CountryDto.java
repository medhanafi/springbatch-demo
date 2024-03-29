package com.sii.springbatchdemo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CountryDto {
    private Long id;

    private String name;

}
