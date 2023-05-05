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

    @Test
    @DisplayName("사진 전체 조회 테스트")
    void getAllPhotos() {
        // given
        Album album1 = Album.createAlbum("testAlbum1");
        Album album2 = Album.createAlbum("testAlbum2");
        albumRepository.save(album1);
        albumRepository.save(album2);

        for (int i = 0; i < 5; i++) {
            PhotoDto photoDto = PhotoDto.builder()
                    .fileName("testPhoto" + i + "withAlbum1")
                    .fileSize(100L)
                    .fileUrl("testFileUrl" + i)
                    .thumbnailUrl("testThumbnailUrl" + i)
                    .build();
            photoService.createPhoto(1L, photoDto);
        }

        for (int i = 0; i < 7; i++) {
            PhotoDto photoDto = PhotoDto.builder()
                    .fileName("testPhoto" + i + "withAlbum2")
                    .fileSize(100L)
                    .fileUrl("testFileUrl" + i)
                    .thumbnailUrl("testThumbnailUrl" + i)
                    .build();
            photoService.createPhoto(2L, photoDto);
        }

        // when
        int photoCount1 = photoService.getPhotoList(1L, "byDate", "Album1", "desc").size();
        int photoCount2 = photoService.getPhotoList(2L, "byDate", "Album2", "desc").size();

        // then
        assertEquals(5, photoCount1);
        assertEquals(7, photoCount2);
    }

    @Test
    @DisplayName("사진 아이디로 조회 테스트")
    void getPhotoById() {
        // given
        Album album = Album.createAlbum("testAlbum");
        albumRepository.save(album);

        PhotoDto photoDto = PhotoDto.builder()
                .fileName("testPhoto")
                .fileSize(100L)
                .fileUrl("testFileUrl")
                .thumbnailUrl("testThumbnailUrl")
                .build();
        PhotoDto responsePhotoDto = photoService.createPhoto(1L, photoDto);

        // when
        PhotoDto findPhotoDto = assertDoesNotThrow(() -> photoService.getPhotoById(1L, responsePhotoDto.getId()));

        // then
        assertEquals("testPhoto", findPhotoDto.getFileName());
        assertEquals(100L, findPhotoDto.getFileSize());
        assertEquals("testFileUrl", findPhotoDto.getFileUrl());
        assertEquals("testThumbnailUrl", findPhotoDto.getThumbnailUrl());
    }

    @Test
    @DisplayName("사진 삭제 테스트")
    void deletePhoto() {
        // given
        Album album = Album.createAlbum("testAlbum");
        albumRepository.save(album);

        PhotoDto photoDto = PhotoDto.builder()
                        .fileName("testPhoto")
                        .fileSize(100L)
                        .fileUrl("testFileUrl")
                        .thumbnailUrl("testThumbnailUrl")
                        .build();
        PhotoDto responsePhotoDto = photoService.createPhoto(1L, photoDto);

        // when and then
        assertDoesNotThrow(() -> photoService.deletePhoto(1L, responsePhotoDto.getId()));
        assertThrows(NoSuchElementException.class, () -> photoService.getPhotoById(1L, responsePhotoDto.getId()));
    }
}