package com.squarecross.photoalbum.repository;

import com.squarecross.photoalbum.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByAlbumIdAndFileNameContainingOrderByUploadedAtAsc(Long id, String keyword);
    List<Photo> findByAlbumIdAndFileNameContainingOrderByUploadedAtDesc(Long id, String keyword);
    List<Photo> findByAlbumIdAndFileNameContainingOrderByFileNameAsc(Long id, String keyword);
    List<Photo> findByAlbumIdAndFileNameContainingOrderByFileNameDesc(Long id, String keyword);

    Optional<Photo> findByAlbumIdAndId(Long albumId, Long photoId);

    void deleteByAlbumIdAndId(Long albumId, Long photoId);
}
