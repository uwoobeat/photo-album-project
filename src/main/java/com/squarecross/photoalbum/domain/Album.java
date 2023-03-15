package com.squarecross.photoalbum.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "album", schema = "photo_album", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"album_id"})
})
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id", unique = true, nullable = false)
    private Long id;

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<Photo>();

    @Column(name = "album_name", unique = false, nullable = false)
    private String name;

    @Column(name = "created_at", unique = false, nullable = true)
    @CreationTimestamp
    private Date createdAt;

    private int photoCount = 0;

    @Builder
    public Album(String name) {
        this.name = name;
    }

    public static Album createAlbum(String name) {
        return Album.builder()
                .name(name)
                .build();
    }
}
