package com.watermelon.dto.music;

import lombok.Builder;
import lombok.Getter;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

@Getter
public class MusicUpdateRequestDto {
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

    private List<Long> artistIdList;
    private Long albumId;

    @Builder
    public MusicUpdateRequestDto(String title, String lyrics, String composer, String songwriter, String arranger,
                                 Integer likes, Integer hits, Date releaseDate, Date deletedAt, Boolean isTitle,
                                 Blob thumbnail, String genre, List<Long> artistIdList, Long albumId) {
        this.title = title;
        this.lyrics = lyrics;
        this.composer = composer;
        this.songwriter = songwriter;
        this.arranger = arranger;
        this.likes = likes;
        this.hits = hits;
        this.releaseDate = releaseDate;
        this.deletedAt = deletedAt;
        this.isTitle = isTitle;
        this.thumbnail = thumbnail;
        this.genre = genre;
        this.artistIdList = artistIdList;
        this.albumId = albumId;
    }
}
