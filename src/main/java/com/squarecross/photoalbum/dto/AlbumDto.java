package com.squarecross.photoalbum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @PastOrPresent
    private Date createdAt;
    @Size(min = 0)
    private int photoCount;
}
