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
    void getAllAlbums() {
    }
    @Test
    void findAlbumsById() {
    }
}