package com.watermelon.dto.artist;

import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.dto.artist_album.ArtistToAlbumResponseDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 개별 조회와 목록 조회를 위한 Response Dto입니다.
 */

@Getter
public class ArtistReadResponseDto {

    private Long id;
    private String name;
    private String information;
    private String fan_club;
    private String activity;
    private String genre;
    private String nationality;
    private String agency;
    private Boolean is_group;
    private Boolean is_concoction;
    private Date debut;
    private Date deleted_at;

    // 앨범 정보만을 추출하기 위한 Dto
    private List<ArtistToAlbumResponseDto> albums;

    private List<ArtistOnlyResponseDto> members;
    private ArtistOnlyResponseDto group;

    public ArtistReadResponseDto(Artist entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.information = entity.getInformation();
        this.fan_club = entity.getFan_club();
        this.activity = entity.getActivity();
        this.genre = entity.getGenre();
        this.nationality = entity.getNationality();
        this.agency = entity.getAgency();
        this.is_concoction = entity.getIs_concoction();
        this.is_group = entity.getIs_group();
        this.debut = entity.getDebut();
        this.deleted_at = entity.getDeleted_at();

        if (entity.getGroup() != null && entity.getGroup().getDeleted_at() == null) {
            this.group = new ArtistOnlyResponseDto(entity.getGroup());
        }

        // 여러 앨범 리스트를 순회하면서 앨범 정보를 필터링합니다
        this.albums = new ArrayList<>();
        for (ArtistAlbum artistAlbum : entity.getArtistAlbums()) {
            ArtistToAlbumResponseDto responseDto = new ArtistToAlbumResponseDto(artistAlbum);
            if (responseDto.getAlbum().getDeleted_at() == null){
                this.albums.add(new ArtistToAlbumResponseDto(artistAlbum));
            }
        }

        this.members = new ArrayList<>();
        for (Artist member : entity.getMembers()) {
            ArtistOnlyResponseDto responseDto = new ArtistOnlyResponseDto(member);
            if (responseDto.getDeleted_at() == null) {
                this.members.add(responseDto);
            }
        }
    }
}
