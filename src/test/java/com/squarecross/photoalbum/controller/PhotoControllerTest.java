package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.repository.AlbumRepository;
import com.squarecross.photoalbum.repository.PhotoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhotoControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private AlbumRepository albumRepository;

    @AfterEach
    void tearDown() {
        photoRepository.deleteAll();
    }

    @Test
    @DisplayName("사진 생성 POST 테스트")
    void createPhoto() {
        // given
        Album album = Album.createAlbum("testAlbum");
        albumRepository.save(album);

        PhotoDto photoDto = PhotoDto.builder()
                .fileName("testPhoto")
                .fileSize(100L)
                .fileUrl("testFileUrl")
                .thumbnailUrl("testThumbnailUrl")
                .build();

        // when
        ResponseEntity<PhotoDto> responseEntity = restTemplate.postForEntity("/api/v1/albums/1/photos", photoDto, PhotoDto.class);

        // then
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}