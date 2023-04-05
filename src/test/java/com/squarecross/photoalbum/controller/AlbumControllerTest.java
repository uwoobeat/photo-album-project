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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Test
    @DisplayName("전체 앨범 목록 GET 테스트")
    void getAlbumList() {
        // given
        Album album1 = Album.createAlbum("testAlbum1");
        Album album2 = Album.createAlbum("testAlbum2");
        Album album3 = Album.createAlbum("testAlbum3");
        AlbumDto albumDto1 = AlbumMapper.toDto(album1);
        AlbumDto albumDto2 = AlbumMapper.toDto(album2);
        AlbumDto albumDto3 = AlbumMapper.toDto(album3);
        restTemplate.postForEntity("/api/v1/albums", albumDto2, AlbumDto.class);
        restTemplate.postForEntity("/api/v1/albums", albumDto1, AlbumDto.class);
        restTemplate.postForEntity("/api/v1/albums", albumDto3, AlbumDto.class);

        // when
        ResponseEntity<List<AlbumDto>> responseEntity = restTemplate.exchange("/api/v1/albums", HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumDto>>() {
        });

        // then
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(3, responseEntity.getBody().size());
        assertEquals("testAlbum2", responseEntity.getBody().get(0).getName());
        assertEquals("testAlbum1", responseEntity.getBody().get(1).getName());
        assertEquals("testAlbum3", responseEntity.getBody().get(2).getName());
    }

    @Test
    @DisplayName("sort, keyword, orderBy로 앨범 검색 및 결과 정렬 GET 테스트")
    void getSortedAlbumList() {
        // given

        List<Album> albumList = new ArrayList<Album>();
        Album ignoredAlbum = Album.createAlbum("ignoredAlbum");
        albumList.add(ignoredAlbum);

        for (int i = 1; i <= 4; i++) {
            Album album = Album.createAlbum("testAlbum" + i);
            albumList.add(album);
        }

        Collections.shuffle(albumList);

        for (Album album : albumList) {
            AlbumDto albumDto = AlbumMapper.toDto(album);
            restTemplate.postForEntity("/api/v1/albums", albumDto, AlbumDto.class);
        }

        // when
        ResponseEntity<List<AlbumDto>> responseEntity = restTemplate.exchange("/api/v1/albums?sort=byName&keyword=testAlbum&orderBy=desc", HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumDto>>() {
        });

        // then
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(4, responseEntity.getBody().size());
        assertEquals("testAlbum4", responseEntity.getBody().get(0).getName());
        assertEquals("testAlbum3", responseEntity.getBody().get(1).getName());
        assertEquals("testAlbum2", responseEntity.getBody().get(2).getName());
        assertEquals("testAlbum1", responseEntity.getBody().get(3).getName());
    }


    @Test
    @DisplayName("앨범 ID로 앨범 GET 테스트")
    void getAlbumById() {
        // given
        Album album = Album.createAlbum("testAlbum");
        AlbumDto albumDto = AlbumMapper.toDto(album);
        ResponseEntity<AlbumDto> postResponseEntity = restTemplate.postForEntity("/api/v1/albums", albumDto, AlbumDto.class);

        // when
        ResponseEntity<AlbumDto> responseEntity = restTemplate.getForEntity("/api/v1/albums/" + postResponseEntity.getBody().getId(), AlbumDto.class);

        // then
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("testAlbum", responseEntity.getBody().getName());
        assertNotNull(responseEntity.getBody().getId());
        assertNotNull(responseEntity.getBody().getCreatedAt());
        assertEquals(0, responseEntity.getBody().getPhotoCount());
    }
}
