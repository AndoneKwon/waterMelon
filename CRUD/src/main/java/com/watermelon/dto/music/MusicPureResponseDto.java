package com.watermelon.dto.music;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.music.Music;
import lombok.Getter;

import java.sql.Blob;
import java.util.Date;

@Getter
public class MusicPureResponseDto {
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

    public MusicPureResponseDto(Music entity) {
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
    }
}
