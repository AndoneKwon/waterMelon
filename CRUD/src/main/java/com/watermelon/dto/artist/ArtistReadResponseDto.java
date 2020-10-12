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
    private String fanClub;
    private String activity;
    private String genre;
    private String nationality;
    private String agency;
    private String gender;
    private String type;
    private String debutMusic;
    private Boolean isGroup;
    private Boolean isConcoction;
    private Date debut;
    private Date deletedAt;

    // 앨범 정보만을 추출하기 위한 Dto
    private List<ArtistToAlbumResponseDto> albums;

    private List<ArtistPureResponseDto> members;
    private ArtistPureResponseDto group;

    public ArtistReadResponseDto(Artist entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.information = entity.getInformation();
        this.fanClub = entity.getFanClub();
        this.activity = entity.getActivity();
        this.genre = entity.getGenre();
        this.nationality = entity.getNationality();
        this.agency = entity.getAgency();
        this.type = entity.getType();
        this.gender = entity.getGender();
        this.debutMusic = entity.getDebutMusic();
        this.isConcoction = entity.getIsConcoction();
        this.isGroup = entity.getIsGroup();
        this.debut = entity.getDebut();
        this.deletedAt = entity.getDeletedAt();

        if (entity.getGroup() != null && entity.getGroup().getDeletedAt() == null) {
            this.group = new ArtistPureResponseDto(entity.getGroup());
        }

        // 여러 앨범 리스트를 순회하면서 앨범 정보를 필터링합니다
        this.albums = new ArrayList<>();
        for (ArtistAlbum artistAlbum : entity.getArtistAlbums()) {
            ArtistToAlbumResponseDto responseDto = new ArtistToAlbumResponseDto(artistAlbum);
            if (responseDto.getAlbum().getDeletedAt() == null){
                this.albums.add(new ArtistToAlbumResponseDto(artistAlbum));
            }
        }

        this.members = new ArrayList<>();
        for (Artist member : entity.getMembers()) {
            ArtistPureResponseDto responseDto = new ArtistPureResponseDto(member);
            if (responseDto.getDeletedAt() == null) {
                this.members.add(responseDto);
            }
        }
    }
}
