package com.sii.springbatchdemo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DirectorDto {
    private Long id;

    private String fullName;

}
