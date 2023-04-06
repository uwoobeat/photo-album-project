package com.squarecross.photoalbum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime createdAt;
    @Size(min = 0)
    private int photoCount;
    private List<String> thumbnailUrls;
}
