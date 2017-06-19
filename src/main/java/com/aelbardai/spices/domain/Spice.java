package com.aelbardai.spices.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Spice {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String scientificName;
    private Double price;
    private String foodUsage;
    private String imagePath;
    private String benefits;
    private String cultivationArea;
}
