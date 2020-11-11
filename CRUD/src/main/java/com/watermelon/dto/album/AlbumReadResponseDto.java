package com.watermelon.dto.album;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.dto.artist.ArtistPureResponseDto;
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

    public AlbumReadResponseDto(Album entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.type = entity.getType();
        this.publishDate = entity.getPublishDate();
        this.publisher = entity.getPublisher();
        this.agency = entity.getAgency();
        this.information = entity.getInformation();
        this.deletedAt = entity.getDeletedAt();

        // 여러 앨범 리스트를 순회하면서 앨범 정보를 필터링합니다
        this.artists = new ArrayList<>();
        for (ArtistAlbum artistAlbum : entity.getArtistAlbums()) {
            ArtistPureResponseDto responseDto = new ArtistPureResponseDto(artistAlbum.getArtist());
            if (responseDto.getDeletedAt() == null) {
                this.artists.add(responseDto);
            }
        }
    }
}
