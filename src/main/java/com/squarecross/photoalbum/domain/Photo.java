package com.squarecross.photoalbum.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "photo", schema = "photo_album", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"photo_id"})
})
@Getter
@Setter
@NoArgsConstructor
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
}
