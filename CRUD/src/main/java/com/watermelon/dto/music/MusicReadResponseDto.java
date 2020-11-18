package com.watermelon.dto.music;

import com.watermelon.domain.artist_music.ArtistMusic;
import com.watermelon.domain.music.Music;
import com.watermelon.dto.album.AlbumPureResponseDto;
import com.watermelon.dto.artist.ArtistPureResponseDto;
import lombok.Getter;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class MusicReadResponseDto {
    private Long id;
    private String title;
    private String lyrics;
    private String composer;
    private String songwriter;
    private String arranger;
    private Integer likes;
    private Integer hits;
    private Date releaseDate;
    private Date deletedAt;
    private Boolean isTitle;
    private Blob thumbnail;
    private String genre;
    private AlbumPureResponseDto album;

    private List<ArtistPureResponseDto> artists;

    public MusicReadResponseDto(Music entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.lyrics = entity.getLyrics();
        this.composer = entity.getComposer();
        this.songwriter = entity.getSongwriter();
        this.arranger = entity.getArranger();
        this.likes = entity.getLikes();
        this.hits = entity.getHits();
        this.releaseDate = entity.getReleaseDate();
        this.deletedAt = entity.getDeletedAt();
        this.isTitle = entity.getIsTitle();
        this.thumbnail = entity.getThumbnail();
        this.genre = entity.getGenre();

        if (entity.getAlbum() != null && entity.getAlbum().getDeletedAt() == null) {
            this.album = new AlbumPureResponseDto(entity.getAlbum());
        }

        // 연결된 아티스트 정보를 필터링해서 넣어줍니다.
        this.artists = new ArrayList<>();
        for (ArtistMusic artistMusic : entity.getArtistMusics()) {
            if (artistMusic.getDeletedAt() != null) {
                continue;
            }
            ArtistPureResponseDto responseDto = new ArtistPureResponseDto(artistMusic.getArtist());
            if (responseDto.getDeletedAt() == null) {
                this.artists.add(responseDto);
            }
        }
    }
}
