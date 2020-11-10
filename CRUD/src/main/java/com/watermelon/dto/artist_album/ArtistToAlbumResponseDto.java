package com.watermelon.dto.artist_album;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.dto.album.AlbumResponseDto;
import lombok.Getter;

@Getter
public class ArtistToAlbumResponseDto {

    private AlbumResponseDto album;

    public ArtistToAlbumResponseDto(ArtistAlbum entity) {
        this.album = new AlbumResponseDto(entity.getAlbum());
    }
}
