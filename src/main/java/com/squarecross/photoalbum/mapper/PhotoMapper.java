package com.squarecross.photoalbum.mapper;

import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.PhotoDto;

public class PhotoMapper {
    public static Photo toEntity(PhotoDto photoDto) {
        return Photo.dtoBuilder()
                .id(photoDto.getId())
                .fileName(photoDto.getFileName())
                .fileSize(photoDto.getFileSize())
                .fileUrl(photoDto.getFileUrl())
                .thumbnailUrl(photoDto.getThumbnailUrl())
                .uploadedAt(photoDto.getUploadedAt())
                .build();
    }

    public static PhotoDto toDto(Photo photo) {
        return PhotoDto.builder()
                .id(photo.getId())
                .fileName(photo.getFileName())
                .fileSize(photo.getFileSize())
                .fileUrl(photo.getFileUrl())
                .thumbnailUrl(photo.getThumbnailUrl())
                .uploadedAt(photo.getUploadedAt())
                .build();
    }
}