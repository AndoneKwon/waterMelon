package com.watermelon.domain.artist_album;

import com.watermelon.domain.BaseTimeEntity;
import com.watermelon.domain.album.Album;
import com.watermelon.domain.artist.Artist;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    @Builder
    public ArtistAlbum(Artist artist, Album album, Date deletedAt) {
        this.artist = artist;
        this.album = album;
        this.deletedAt = deletedAt;
    }

    public void setArtist(Artist artist) {
        if (this.artist != null) {
            this.artist.removeRelation(this);
        }
        this.artist = artist;
        artist.addRelation(this);
    }
    public void setAlbum(Album album) {
        if (this.album != null) {
            this.album.removeRelation(this);
        }
        this.album = album;
        album.addRelation(this);
    }
}
