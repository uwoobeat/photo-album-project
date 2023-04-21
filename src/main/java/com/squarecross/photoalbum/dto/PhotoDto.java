package com.squarecross.photoalbum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDto {
    // TODO: @Valid 사용하여 DTO 검증 로직 추가
    private Long id;
    @NotBlank
    private String fileName;
    @Size(min = 0)
    private Long fileSize;
    @NotBlank
    private String fileUrl;
    @NotBlank
    private String thumbnailUrl;
    @PastOrPresent
    private LocalDateTime uploadedAt;
}
