package com.aelbardai.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Entity
public class Spice {

    @Id
    private Long id;
    private String name;
    private String scientificName;
    private Double price;
    private String foodUsage;
    private String imagePath;
    private String benefits;
    private String cultivationArea;
}
