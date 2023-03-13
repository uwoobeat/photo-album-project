package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AlbumServiceTest {

    @Test
    @DisplayName("앨범을 생성한다.")
    // create test code for createAlbum() for Album class, which have id, photos, name, createdAt field using builder pattern
    void createAlbum() {
        // given
        Album album = Album.builder()
                .name("test")
                .build();

        // when
        Album savedAlbum = albumService.createAlbum(album);

        // then
        assertEquals(album, savedAlbum);
    }

    @Test
    void getAllAlbums() {
    }
    @Test
    void findAlbumsById() {
    }
}