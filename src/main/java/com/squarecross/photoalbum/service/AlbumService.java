package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.mapper.AlbumMapper;
import com.squarecross.photoalbum.repository.AlbumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public AlbumDto createAlbum(AlbumDto albumDto) throws IOException {
        Album album = AlbumMapper.toEntity(albumDto);
        Album saveAlbum = albumRepository.save(album);
        createAlbumDirectories(saveAlbum);
        return AlbumMapper.toDto(saveAlbum);
    }

    @Transactional(readOnly = true)
    public List<AlbumDto> getAlbumList(String sort, String keyword, String orderBy) {
        List<Album> albums;
        if ("byDate".equals(sort)) {
            if ("asc".equalsIgnoreCase(orderBy)) {
                albums = albumRepository.findByNameContainingOrderByCreatedAtAsc(keyword);
            } else {
                albums = albumRepository.findByNameContainingOrderByCreatedAtDesc(keyword);
            }
        } else if ("byName".equals(sort)) {
            if ("asc".equalsIgnoreCase(orderBy)) {
                albums = albumRepository.findByNameContainingOrderByNameAsc(keyword);
            } else {
                albums = albumRepository.findByNameContainingOrderByNameDesc(keyword);
            }
        } else {
            throw new IllegalArgumentException("잘못된 정렬 방식입니다.");
        }
        return albums.stream()
                .map(AlbumMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AlbumDto getAlbumById(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 앨범이 없습니다."));
        return AlbumMapper.toDto(album);
    }

    @Transactional(readOnly = true)
    public List<AlbumDto> getAlbumsByName(String name) {
        List<Album> albums = albumRepository.findByName(name);
        return albums.stream()
                .map(AlbumMapper::toDto)
                .collect(Collectors.toList());
    }

    public void createAlbumDirectories(Album album) throws IOException {
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX + "/photos/" + album.getId()));
        Files.createDirectories((Paths.get(Constants.PATH_PREFIX + "/thumbnails/" + album.getId())));
    }
}
