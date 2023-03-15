package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.repository.AlbumRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AlbumServiceTest {
    @Autowired EntityManager em;
    @Autowired AlbumService albumService;
    @Autowired AlbumRepository albumRepository;


    @Test
    @DisplayName("앨범 생성 테스트")
    void createAlbum() {
        // given
        Album album = Album.createAlbum("testAlbum");
        em.persist(album);

        //when
        Album findAlbum = albumRepository.findByName("testAlbum").get(0);


        // then
        assertNotNull(album.getId());
        assertEquals("testAlbum", album.getName());
        assertEquals(0, album.getPhotos().size());
        assertNotNull(album.getCreatedAt());
    }

    @Test
    @DisplayName("전체 앨범 가져오기 테스트")
    void getAllAlbums() {
        // given
        Album album1 = Album.createAlbum("testAlbum1");
        Album album2 = Album.createAlbum("testAlbum2");
        em.persist(album1);
        em.persist(album2);

        // when
        int albumCount = albumService.getAllAlbums().size();

        // then
        assertEquals(2, albumCount);
    }

    @Test
    void findAlbumsById() {
    }
}