package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {
    private final AlbumService albumService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable final Long id) {
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }
}
