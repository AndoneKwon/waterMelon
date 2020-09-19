package com.watermelon.dto.album;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.dto.artist_album.AlbumToArtistResponseDto;
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
    private Date publish_date;
    private String publisher;
    private String agency;
    private String information;
    private Date deleted_at;

    // 아티스트 정보만을 추출하기 위한 Dto
    private List<AlbumToArtistResponseDto> artists;

    public AlbumReadResponseDto(Album entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.type = entity.getType();
        this.publish_date = entity.getPublish_date();
        this.publisher = entity.getPublisher();
        this.agency = entity.getAgency();
        this.information = entity.getInformation();
        this.deleted_at = entity.getDeleted_at();

        // 여러 앨범 리스트를 순회하면서 앨범 정보를 필터링합니다
        this.artists = new ArrayList<>();
        for (ArtistAlbum artistAlbum : entity.getArtistAlbums()) {
            AlbumToArtistResponseDto responseDto = new AlbumToArtistResponseDto(artistAlbum);
            if (responseDto.getArtist().getDeleted_at() == null) {
                this.artists.add(new AlbumToArtistResponseDto(artistAlbum));
            }
        }
    }
}
