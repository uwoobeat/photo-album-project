package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PhotoServiceTest {
    @Autowired PhotoService photoService;
    @Autowired AlbumService albumService;
    @Autowired PhotoRepository photoRepository;
}