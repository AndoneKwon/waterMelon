package com.watermelon.domain.artist_music;

import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.music.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistMusicRepository extends JpaRepository<ArtistMusic, Long> {
    ArtistMusic findByMusicAndArtist(Music music, Artist artist);
}
