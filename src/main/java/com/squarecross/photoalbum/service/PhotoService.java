package com.squarecross.photoalbum.service;

import com.squarecross.photoalbum.repository.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PhotoService {
    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }
}
