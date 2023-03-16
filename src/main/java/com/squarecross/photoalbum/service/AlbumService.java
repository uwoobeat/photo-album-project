package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.repository.AlbumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public Album saveAlbum(Album album) {
        return albumRepository.save(album);
    }

    @Transactional(readOnly = true)
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Album> findAlbumsByName(String name) {
        return albumRepository.findByName(name);
    }
}
