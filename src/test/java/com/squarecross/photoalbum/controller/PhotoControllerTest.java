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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

    @Test
    @DisplayName("사진 목록 조회 GET 테스트")
    void getPhotoList() {
        // given
        Album album = Album.createAlbum("testAlbum");
        albumRepository.save(album);

        for (int i = 0; i < 3; i++) {
            PhotoDto photoDto = PhotoDto.builder()
                    .fileName("testPhoto" + i)
                    .fileSize(100L * i)
                    .fileUrl("testFileUrl" + i)
                    .thumbnailUrl("testThumbnailUrl" + i)
                    .build();
            restTemplate.postForEntity("/api/v1/albums/1/photos", photoDto, PhotoDto.class);
        }

        // when
        ResponseEntity<List<PhotoDto>> responseEntity = restTemplate.exchange("/api/v1/albums/1/photos", HttpMethod.GET, null, new ParameterizedTypeReference<List<PhotoDto>>() {});

        // then
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(3, responseEntity.getBody().size());
    }

    @Test
    @DisplayName("사진 ID로 조회 GET 테스트")
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
        restTemplate.postForEntity("/api/v1/albums/1/photos", photoDto, PhotoDto.class);

        // when
        ResponseEntity<PhotoDto> responseEntity = restTemplate.getForEntity("/api/v1/albums/1/photos/1", PhotoDto.class);

        // then
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    @DisplayName("사진 삭제 DELETE 테스트")
    void deletePhoto() {
        // given
        Album album = Album.createAlbum("testAlbum");
        albumRepository.save(album);

        PhotoDto photoDto1 = PhotoDto.builder()
                .fileName("testPhoto1")
                .fileSize(100L)
                .fileUrl("testFileUrl1")
                .thumbnailUrl("testThumbnailUrl1")
                .build();

        ResponseEntity<PhotoDto> postForEntity1 = restTemplate.postForEntity("/api/v1/albums/1/photos", photoDto1, PhotoDto.class);


        // when
        restTemplate.delete("/api/v1/albums/1/photos/" + postForEntity1.getBody().getId());

        // then
        ResponseEntity<PhotoDto> responseEntity = restTemplate.getForEntity("/api/v1/albums/1/photos/" + postForEntity1.getBody().getId(), PhotoDto.class);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }
}