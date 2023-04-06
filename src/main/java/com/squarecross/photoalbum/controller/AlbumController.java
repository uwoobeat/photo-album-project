package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.dto.AlbumDto;
import com.squarecross.photoalbum.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable final Long id) {
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }

    @PostMapping(value = "")
    public ResponseEntity<AlbumDto> createAlbum(@RequestBody final AlbumDto albumDto) throws IOException {
        return ResponseEntity.ok(albumService.createAlbum(albumDto));
    }

    @GetMapping(value = "")
    public ResponseEntity<List<AlbumDto>> getAlbumList(@RequestParam(value = "sort", required = false, defaultValue = "byDate") final String sort,
                                                       @RequestParam(value = "keyword", required = false, defaultValue = "") final String keyword,
                                                       @RequestParam(value = "orderBy", required = false, defaultValue = "desc") final String orderBy) {
        return ResponseEntity.ok(albumService.getAlbumList(sort, keyword, orderBy));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AlbumDto> updateAlbumName(@PathVariable final Long id, @RequestBody final AlbumDto albumDto) {
        return ResponseEntity.ok(albumService.updateAlbumName(id, albumDto));
    }
}
