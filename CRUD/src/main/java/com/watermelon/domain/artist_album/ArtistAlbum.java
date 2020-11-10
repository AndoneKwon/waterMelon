package com.watermelon.domain.artist_album;

import com.watermelon.domain.BaseTimeEntity;
import com.watermelon.domain.album.Album;
import com.watermelon.domain.artist.Artist;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class ArtistAlbum extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
}
