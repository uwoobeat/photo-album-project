package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.domain.Photo;
import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PhotoServiceTest {
    @Autowired
    PhotoService photoService;
    @Autowired
    AlbumService albumService;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    AlbumRepository albumRepository;

    @AfterEach
    void afterEach() {
        photoRepository.deleteAll();
        albumRepository.deleteAll();
    }

    @Test
    @DisplayName("사진 생성 테스트")
    void createPhoto() {
        // given
        Album album = Album.createAlbum("testAlbum");
        albumRepository.save(album);

        PhotoDto requestPhotoDto = PhotoDto.builder()
                .fileName("testPhoto")
                .fileSize(100L)
                .fileUrl("testFileUrl")
                .thumbnailUrl("testThumbnailUrl")
                .build();

        // when
        PhotoDto responsePhotoDto = assertDoesNotThrow(() -> photoService.createPhoto(1L, requestPhotoDto));
        Photo findPhoto = photoRepository.findById(responsePhotoDto.getId()).orElseThrow();

        // then
        assertEquals(1L, findPhoto.getAlbum().getId());
        assertSame(album, findPhoto.getAlbum());
        assertEquals("testPhoto", responsePhotoDto.getFileName());
        assertEquals(100L, responsePhotoDto.getFileSize());
        assertEquals("testFileUrl", responsePhotoDto.getFileUrl());
        assertEquals("testThumbnailUrl", responsePhotoDto.getThumbnailUrl());
    }
}