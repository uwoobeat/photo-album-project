package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.mapper.AlbumMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlbumControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AlbumController albumController;

    @Test
    @DisplayName("앨범 생성 POST 테스트")
    void createAlbum() {
        // given
        Album album = Album.createAlbum("testAlbum");
        AlbumDto albumDto = AlbumMapper.toDto(album);

        // when
        ResponseEntity<AlbumDto> responseEntity = restTemplate.postForEntity("/api/v1/albums", albumDto, AlbumDto.class);

        // then
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("testAlbum", responseEntity.getBody().getName());
        assertNotNull(responseEntity.getBody().getId());
        assertNotNull(responseEntity.getBody().getCreatedAt());
        assertEquals(0, responseEntity.getBody().getPhotoCount());
    }

}
