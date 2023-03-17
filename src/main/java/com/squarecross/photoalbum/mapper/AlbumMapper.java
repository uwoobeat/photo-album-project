package com.squarecross.photoalbum.mapper;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.dto.AlbumDto;

public class AlbumMapper {

    public static Album toEntity(AlbumDto albumDto) {
        return Album.dtoBuilder()
                .id(albumDto.getId())
                .name(albumDto.getName())
                .createdAt(albumDto.getCreatedAt())
                .photoCount(albumDto.getPhotoCount())
                .build();
    }

    public static AlbumDto toDto(Album album) {
        return AlbumDto.builder()
                .id(album.getId())
                .name(album.getName())
                .createdAt(album.getCreatedAt())
                .photoCount(album.getPhotoCount())
                .build();
    }
}
