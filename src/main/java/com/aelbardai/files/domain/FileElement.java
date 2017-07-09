package com.aelbardai.files.domain;


import com.aelbardai.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name="files")
@NoArgsConstructor
@AllArgsConstructor
public class FileElement {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String path;
    private String extension;
    private String contentType;
    private Long size;
    private String tags;
    private String category;
    private Long parentId;
    @ManyToOne
    @JoinColumn(name = "user.id")
    private User user;
}
