package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.mapper.PhotoMapper;
import com.squarecross.photoalbum.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotoService {
    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public List<PhotoDto> getPhotoList(Long id, String sort, String keyword, String orderBy) {
        List<Photo> photos;

        switch (sort) {
            case "byDate":
                if ("asc".equalsIgnoreCase(orderBy)) {
                    photos = photoRepository.findByAlbumIdAndNameContainingOrderByCreatedAtAsc(id, keyword);
                } else if ("desc".equalsIgnoreCase(orderBy)) {
                    photos = photoRepository.findByAlbumIdAndNameContainingOrderByCreatedAtDesc(id, keyword);
                } else {
                    throw new IllegalArgumentException("잘못된 정렬 방식입니다.");
                }
                break;
            case "byName":
                if ("asc".equalsIgnoreCase(orderBy)) {
                    photos = photoRepository.findByAlbumIdAndNameContainingOrderByNameAsc(id, keyword);
                } else if ("desc".equalsIgnoreCase(orderBy)){
                    photos = photoRepository.findByAlbumIdAndNameContainingOrderByNameDesc(id, keyword);
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
}
