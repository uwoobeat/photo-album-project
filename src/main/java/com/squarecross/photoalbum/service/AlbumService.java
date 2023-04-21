package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.mapper.AlbumMapper;
import com.squarecross.photoalbum.repository.AlbumRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@Slf4j
public class AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public AlbumDto createAlbum(AlbumDto albumDto) throws IOException {
        Album album = AlbumMapper.toEntity(albumDto);
        Album saveAlbum = albumRepository.save(album);
        try {
            createAlbumDirectories(album);
        } catch (IOException e) {
            log.error(album + "앨범 디렉토리 생성 실패", e);
            throw new IOException("앨범 디렉토리 생성 실패");
        }
        return AlbumMapper.toDto(saveAlbum);
    }

    @Transactional(readOnly = true)
    public List<AlbumDto> getAlbumList(String sort, String keyword, String orderBy) {
        List<Album> albums;

        switch (sort) {
            case "byDate":
                if ("asc".equalsIgnoreCase(orderBy)) {
                    albums = albumRepository.findByNameContainingOrderByCreatedAtAsc(keyword);
                } else if ("desc".equalsIgnoreCase(orderBy)) {
                    albums = albumRepository.findByNameContainingOrderByCreatedAtDesc(keyword);
                } else {
                    throw new IllegalArgumentException("잘못된 정렬 방식입니다.");
                }
                break;
            case "byName":
                if ("asc".equalsIgnoreCase(orderBy)) {
                    albums = albumRepository.findByNameContainingOrderByNameAsc(keyword);
                } else if ("desc".equalsIgnoreCase(orderBy)){
                    albums = albumRepository.findByNameContainingOrderByNameDesc(keyword);
                } else {
                    throw new IllegalArgumentException("잘못된 정렬 방식입니다.");
                }
                break;
            default:
                throw new IllegalArgumentException("잘못된 정렬 방식입니다.");
        }

        return albums.stream()
                .map(AlbumMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AlbumDto getAlbumById(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new NoSuchElementException("id: " + id + "인 앨범이 없습니다."));
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
        Files.createDirectories(Paths.get(Constants.PATH_PREFIX + "/original/" + album.getId()));
        Files.createDirectories((Paths.get(Constants.PATH_PREFIX + "/thumb/" + album.getId())));
    }

    public void deleteAlbumDirectories(Album album) throws IOException {
        String originalPath = Constants.PATH_PREFIX + "/original/" + album.getId();
        String thumbPath = Constants.PATH_PREFIX + "/thumb/" + album.getId();

        Stream.concat(Files.walk(Paths.get(originalPath)), Files.walk(Paths.get(thumbPath)))
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        log.error("파일 삭제 실패: " + path, e);
                    }
                });

        Files.deleteIfExists(Paths.get(originalPath));
        Files.deleteIfExists(Paths.get(thumbPath));
    }

    public AlbumDto updateAlbumName(Long id, AlbumDto albumDto) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 앨범입니다."));
        album.updateAlbumName(albumDto.getName());
        return AlbumMapper.toDto(album);
    }

    public void deleteAlbum(Long id) throws IOException {
        Album album = albumRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 앨범입니다."));
        albumRepository.delete(album);
        try {
            deleteAlbumDirectories(album);
        } catch (IOException e) {
            log.error(album + "앨범 디렉토리 삭제 실패", e);
            throw new IOException("앨범 디렉토리 삭제 실패");
        }
    }
}
