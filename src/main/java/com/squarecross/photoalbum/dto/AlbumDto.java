package com.squarecross.photoalbum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {
    private Long id;
    private String name;
    private Date createdAt;
    private int photoCount;
}
