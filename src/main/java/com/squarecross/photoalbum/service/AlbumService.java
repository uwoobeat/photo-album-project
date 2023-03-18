package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.mapper.AlbumMapper;
import com.squarecross.photoalbum.repository.AlbumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public AlbumDto saveAlbum(AlbumDto albumDto) {
        Album album = AlbumMapper.toEntity(albumDto);
        Album saveAlbum = albumRepository.save(album);
        return AlbumMapper.toDto(saveAlbum);
    }

    @Transactional(readOnly = true)
    public List<AlbumDto> getAllAlbums() {
        List<Album> albums = albumRepository.findAll();
        return albums.stream()
                .map(AlbumMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AlbumDto> getAlbumsByName(String name) {
        List<Album> albums = albumRepository.findByName(name);
        return albums.stream()
                .map(AlbumMapper::toDto)
                .collect(Collectors.toList());
    }
}
