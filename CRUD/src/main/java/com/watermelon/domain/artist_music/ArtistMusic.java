package com.watermelon.domain.artist_music;

import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.music.Music;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class ArtistMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "music_id")
    private Music music;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    @Builder
    public ArtistMusic(Artist artist, Music music, Date deletedAt) {
        this.artist = artist;
        this.music = music;
        this.deletedAt = deletedAt;
    }
}
