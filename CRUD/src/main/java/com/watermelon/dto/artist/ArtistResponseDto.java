package com.watermelon.dto.artist;

import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.dto.artist_album.ArtistToAlbumResponseDto;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class ArtistResponseDto {

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
    private List<ArtistToAlbumResponseDto> albums;

    public ArtistResponseDto(Artist entity) {
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

        for (ArtistAlbum artistAlbum : entity.getArtistAlbums()) {
            albums.add(new ArtistToAlbumResponseDto(artistAlbum));
        }
    }
}
