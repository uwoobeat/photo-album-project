package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.domain.Album;
import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.repository.AlbumRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

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
        AlbumDto requestAlbumDto = AlbumDto.builder()
                .name("testAlbum")
                .build();

        //when
        AlbumDto responseAlbumDto = assertDoesNotThrow(() -> albumService.createAlbum(requestAlbumDto));

        // then
        assertEquals("testAlbum", responseAlbumDto.getName());
        assertTrue(Files.exists(Paths.get(Constants.PATH_PREFIX + "/original/" + responseAlbumDto.getId())));
        assertTrue(Files.exists(Paths.get(Constants.PATH_PREFIX + "/thumb/" + responseAlbumDto.getId())));
    }

    @Test
    @DisplayName("앨범 목록 모두 검색 테스트")
    void getAllAlbums() {
        // given
        Album album1 = Album.createAlbum("testAlbum1");
        Album album2 = Album.createAlbum("testAlbum2");
        em.persist(album1);
        em.persist(album2);

        // when
        int albumCount = albumService.getAlbumList("byName", "testAlbum", "asc").size();

        // then
        assertEquals(2, albumCount);
    }

    @Test
    @DisplayName("앨범 아이디로 앨범 검색 테스트")
    void getAlbumById() {
        // given
        Album album1 = Album.createAlbum("testAlbum1");
        Album album2 = Album.createAlbum("testAlbum2");
        em.persist(album1);
        em.persist(album2);

        // when
        AlbumDto album = albumService.getAlbumById(album2.getId());

        // then
        assertEquals("testAlbum2", album.getName());
    }

    @Test
    @DisplayName("이름으로 앨범 검색 테스트")
    void getAlbumsByName() {
        // given
        Album album1 = Album.createAlbum("testAlbum1");
        Album album2 = Album.createAlbum("testAlbum2");
        Album album3 = Album.createAlbum("testAlbum3");
        em.persist(album1);
        em.persist(album2);
        em.persist(album3);

        // when
        List<AlbumDto> albums = albumService.getAlbumsByName("testAlbum2");

        // then
        assertEquals(1, albums.size());
        assertEquals("testAlbum2", albums.get(0).getName());
    }

    @Test
    @DisplayName("앨범 목록 검색 테스트")
    void getAlbumList() {
        // given
        Album album1 = Album.createAlbum("testAlbum1");
        Album album2 = Album.createAlbum("testAlbum2");
        Album album3 = Album.createAlbum("testAlbum3");
        Album album4 = Album.createAlbum("ignoredAlbum");
        em.persist(album1);
        em.persist(album2);
        em.persist(album3);
        em.persist(album4);

        // when
        List<AlbumDto> albums = albumService.getAlbumList("byName", "testAlbum", "asc");

        // then
        assertEquals(3, albums.size());
        assertEquals("testAlbum1", albums.get(0).getName());
        assertEquals("testAlbum2", albums.get(1).getName());
        assertEquals("testAlbum3", albums.get(2).getName());
    }

    @Test
    @DisplayName("앨범 목록 생성일로 내림차순 정렬하기 테스트")
    void getAlbumListByDate() {
        // given
        Album album1 = Album.createAlbum("testAlbum1");
        Album album2 = Album.createAlbum("testAlbum2");
        Album album3 = Album.createAlbum("testAlbum3");
        em.persist(album2);
        em.persist(album1);
        em.persist(album3);

        // when
        List<AlbumDto> albums = albumService.getAlbumList("byDate", "testAlbum", "desc");

        // then
        assertEquals(3, albums.size());
        assertEquals("testAlbum3", albums.get(0).getName());
        assertEquals("testAlbum1", albums.get(1).getName());
        assertEquals("testAlbum2", albums.get(2).getName());
    }

    @Test
    @DisplayName("앨범 목록 이름으로 오름차순 정렬하기 테스트")
    void getAlbumListByName() {
        // given
        Album album1 = Album.createAlbum("testAlbum1");
        Album album2 = Album.createAlbum("testAlbum2");
        Album album3 = Album.createAlbum("testAlbum3");
        em.persist(album2);
        em.persist(album1);
        em.persist(album3);

        // when
        List<AlbumDto> albums = albumService.getAlbumList("byName", "testAlbum", "asc");

        // then
        assertEquals(3, albums.size());
        assertEquals("testAlbum1", albums.get(0).getName());
        assertEquals("testAlbum2", albums.get(1).getName());
        assertEquals("testAlbum3", albums.get(2).getName());
    }

    @Test
    @DisplayName("앨범 목록 이름으로 내림차순 정렬하기 테스트")
    void getAlbumListByNameDesc() {
        // given
        Album album1 = Album.createAlbum("testAlbum1");
        Album album2 = Album.createAlbum("testAlbum2");
        Album album3 = Album.createAlbum("testAlbum3");
        em.persist(album2);
        em.persist(album1);
        em.persist(album3);

        // when
        List<AlbumDto> albums = albumService.getAlbumList("byName", "testAlbum", "desc");

        // then
        assertEquals(3, albums.size());
        assertEquals("testAlbum3", albums.get(0).getName());
        assertEquals("testAlbum2", albums.get(1).getName());
        assertEquals("testAlbum1", albums.get(2).getName());
    }

    @Test
    @DisplayName("앨범 이름 변경 테스트")
    void updateAlbumName() {
        // given
        Album album = Album.createAlbum("testAlbum");
        em.persist(album);

        // when
        AlbumDto updatedAlbumDto = new AlbumDto(album.getId(), "updatedAlbumName", album.getCreatedAt(), album.getPhotoCount(), null);
        albumService.updateAlbumName(album.getId(), updatedAlbumDto);

        // then
        assertEquals("updatedAlbumName", album.getName());
    }

    @Test
    @DisplayName("앨범 삭제 테스트")
    void deleteAlbum() {
        // given
        AlbumDto testAlbumDto = AlbumDto.builder()
                .name("testAlbum")
                .build();
        AlbumDto responseAlbumDto = assertDoesNotThrow(() -> albumService.createAlbum(testAlbumDto));

        // when
        assertDoesNotThrow(() -> albumService.deleteAlbum(responseAlbumDto.getId()));

        // then
        assertThrows(NoSuchElementException.class, () -> albumService.getAlbumById(responseAlbumDto.getId()));
        assertFalse(Files.exists(Paths.get(Constants.PATH_PREFIX + "/original/" + responseAlbumDto.getId())));
        assertFalse(Files.exists(Paths.get(Constants.PATH_PREFIX + "/thumb/" + responseAlbumDto.getId())));
    }
}