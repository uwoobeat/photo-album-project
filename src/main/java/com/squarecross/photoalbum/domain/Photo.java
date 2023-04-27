package com.squarecross.photoalbum.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @UpdateTimestamp
    @Column(name = "uploaded_at", unique = false, nullable = true)
    private LocalDateTime uploadedAt;

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Builder
    public Photo(String fileName, Long fileSize, String fileUrl, String thumbnailUrl, LocalDateTime uploadedAt) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileUrl = fileUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.uploadedAt = uploadedAt;
    }

    @Builder(builderMethodName = "dtoBuilder")
    public Photo(Long id, String fileName, Long fileSize, String fileUrl, String thumbnailUrl, LocalDateTime uploadedAt) {
        this.id = id;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileUrl = fileUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.uploadedAt = uploadedAt;
    }

    public static Photo createPhoto(String fileName, Long fileSize, String fileUrl, String thumbnailUrl) {
        return Photo.builder()
                .fileName(fileName)
                .fileSize(fileSize)
                .fileUrl(fileUrl)
                .thumbnailUrl(thumbnailUrl)
                .build();
    }
}
