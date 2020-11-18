package com.watermelon.domain.artist_album;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistAlbumRepository extends JpaRepository<ArtistAlbum, Long> {
    ArtistAlbum findByArtistAndAlbum(Artist artist, Album album);
}
