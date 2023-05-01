package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.mapper.PhotoMapper;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final AlbumRepository albumRepository;

    public PhotoService(PhotoRepository photoRepository, AlbumRepository albumRepository) {
        this.photoRepository = photoRepository;
        this.albumRepository = albumRepository;
    }

    public PhotoDto createPhoto(Long id, PhotoDto photoDto) {
        Photo photo = PhotoMapper.toEntity(photoDto);
        photo.setAlbum(albumRepository.findById(id).orElseThrow(NoSuchElementException::new));
        return PhotoMapper.toDto(photoRepository.save(photo));
    }

    public List<PhotoDto> getPhotoList(Long id, String sort, String keyword, String orderBy) {
        List<Photo> photos;

        switch (sort) {
            case "byDate":
                if ("asc".equalsIgnoreCase(orderBy)) {
                    photos = photoRepository.findByAlbumIdAndFileNameContainingOrderByUploadedAtAsc(id, keyword);
                } else if ("desc".equalsIgnoreCase(orderBy)) {
                    photos = photoRepository.findByAlbumIdAndFileNameContainingOrderByUploadedAtDesc(id, keyword);
                } else {
                    throw new IllegalArgumentException("잘못된 정렬 방식입니다.");
                }
                break;
            case "byName":
                if ("asc".equalsIgnoreCase(orderBy)) {
                    photos = photoRepository.findByAlbumIdAndFileNameContainingOrderByFileNameAsc(id, keyword);
                } else if ("desc".equalsIgnoreCase(orderBy)){
                    photos = photoRepository.findByAlbumIdAndFileNameContainingOrderByFileNameDesc(id, keyword);
                } else {
                    throw new IllegalArgumentException("잘못된 정렬 방식입니다.");
                }
                break;
            default:
                throw new IllegalArgumentException("잘못된 정렬 방식입니다.");
        }

        return photos.stream()
                .map(PhotoMapper::toDto)
                .collect(Collectors.toList());
    }

    public PhotoDto getPhotoById(Long albumId, Long photoId) {
        return PhotoMapper.toDto(photoRepository.findByAlbumIdAndId(albumId, photoId));
    }
}
