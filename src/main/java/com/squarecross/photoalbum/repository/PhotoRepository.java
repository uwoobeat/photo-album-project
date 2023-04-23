package com.squarecross.photoalbum.repository;

import com.squarecross.photoalbum.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByAlbumIdAndNameContainingOrderByCreatedAtAsc(Long id, String keyword);

    List<Photo> findByAlbumIdAndNameContainingOrderByCreatedAtDesc(Long id, String keyword);

    List<Photo> findByAlbumIdAndNameContainingOrderByNameAsc(Long id, String keyword);

    List<Photo> findByAlbumIdAndNameContainingOrderByNameDesc(Long id, String keyword);
}
