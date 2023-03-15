package com.squarecross.photoalbum.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "photo", schema = "photo_album", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"photo_id"})
})
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @Column(name = "file_name", unique = false, nullable = true)
    private String fileName;

    @Column(name = "file_size", unique = false, nullable = true)
    private Long fileSize;

    @Column(name = "file_url", unique = false, nullable = true)
    private String fileUrl;

    @Column(name = "thumbnail_url", unique = false, nullable = true)
    private String thumbnailUrl;

    @Column(name = "uploaded_at", unique = false, nullable = true)
    private Date uploadedAt;

    @Builder
    public Photo(String fileName, Long fileSize, String fileUrl, String thumbnailUrl, Date uploadedAt) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileUrl = fileUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.uploadedAt = uploadedAt;
    }

    public static Photo createPhoto(String fileName, Long fileSize, String fileUrl, String thumbnailUrl, Date uploadedAt) {
        return Photo.builder()
                .fileName(fileName)
                .fileSize(fileSize)
                .fileUrl(fileUrl)
                .thumbnailUrl(thumbnailUrl)
                .uploadedAt(uploadedAt)
                .build();
    }
}
