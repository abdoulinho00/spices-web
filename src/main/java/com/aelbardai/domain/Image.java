package com.aelbardai.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue
    private Long id;
    private String src;
    private String meta;
    private String alt;
}
