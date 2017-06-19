package com.aelbardai.animals.domain;

import com.aelbardai.domain.Image;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Animal {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String scientificName;
    private String family;
    @JsonIgnore
    @OneToMany(targetEntity = Image.class)
    private List<Image> photos;
}
