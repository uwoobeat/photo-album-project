package com.squarecross.photoalbum.controller;

import com.squarecross.photoalbum.dto.PhotoDto;
import com.squarecross.photoalbum.service.PhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/albums/{albumId}/photos")
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping(value = "")
    public ResponseEntity<PhotoDto> createPhoto(@PathVariable("albumId") final Long id, @RequestBody final PhotoDto photoDto) {
        return ResponseEntity.ok(photoService.createPhoto(id, photoDto));
    }

    @GetMapping(value = "")
    public ResponseEntity<List<PhotoDto>> getPhotoList(
            @PathVariable("albumId") final Long id,
            @RequestParam(value = "sort", required = false, defaultValue = "byDate") final String sort,
            @RequestParam(value = "keyword", required = false, defaultValue = "") final String keyword,
            @RequestParam(value = "orderBy", required = false, defaultValue = "desc") final String orderBy
    ) {
        return ResponseEntity.ok(photoService.getPhotoList(id, sort, keyword, orderBy));
    }

    @GetMapping(value = "/{photoId}")
    public ResponseEntity<PhotoDto> getPhotoById(@PathVariable("albumId") final Long albumId, @PathVariable("photoId") final Long photoId) {
        return ResponseEntity.ok(photoService.getPhotoById(albumId, photoId));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Void> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.notFound().build();
    }
}
