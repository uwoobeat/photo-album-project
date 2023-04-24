package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.repository.PhotoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhotoControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PhotoRepository photoRepository;

    @AfterEach
    void tearDown() {
        photoRepository.deleteAll();
    }
}