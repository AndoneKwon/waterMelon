package com.watermelon.dto.album;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.domain.music.Music;
import com.watermelon.dto.artist.ArtistPureResponseDto;
import com.watermelon.dto.music.MusicPureResponseDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 개별 조회와 목록 조회를 위한 Response Dto입니다.
 */

@Getter
public class AlbumReadResponseDto {
    private Long id;
    private String title;
    private String type;
    private Date publishDate;
    private String publisher;
    private String agency;
    private String information;
    private Date deletedAt;

    private List<ArtistPureResponseDto> artists;
    private List<MusicPureResponseDto> musics;

    public AlbumReadResponseDto(Album entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.type = entity.getType();
        this.publishDate = entity.getPublishDate();
        this.publisher = entity.getPublisher();
        this.agency = entity.getAgency();
        this.information = entity.getInformation();
        this.deletedAt = entity.getDeletedAt();

        // 연결된 아티스트 정보를 필터링해서 넣어줍니다.
        this.artists = new ArrayList<>();
        for (ArtistAlbum artistAlbum : entity.getArtistAlbums()) {
            ArtistPureResponseDto responseDto = new ArtistPureResponseDto(artistAlbum.getArtist());
            if (responseDto.getDeletedAt() == null) {
                this.artists.add(responseDto);
            }
        }

        // 연결된 음악 정보를 필터링해서 넣어줍니다.
        this.musics = new ArrayList<>();
        for (Music music : entity.getMusics()) {
            MusicPureResponseDto responseDto = new MusicPureResponseDto(music);
            if (responseDto.getDeletedAt() == null) {
                this.musics.add(responseDto);
            }
        }
    }
}
