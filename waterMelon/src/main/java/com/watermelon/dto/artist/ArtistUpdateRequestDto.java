package com.watermelon.dto.artist;

import com.watermelon.domain.artist.Artist;
import com.watermelon.dto.artist_album.ArtistToAlbumResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class ArtistUpdateRequestDto {

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

    private List<Long> album_id_list;
    private Long artist_id;

    @Builder
    public ArtistUpdateRequestDto(String name, String information, String fan_club, String activity, String genre,
                  String nationality, String agency, Boolean is_concoction,
                  Boolean is_group, Date debut, List<Long> album_id_list, Long artist_id) {
        this.name = name;
        this.information = information;
        this.fan_club = fan_club;
        this.activity = activity;
        this.genre = genre;
        this.nationality = nationality;
        this.agency = agency;
        this.is_concoction = is_concoction;
        this.is_group = is_group;
        this.debut = debut;
        this.album_id_list = album_id_list;
        this.artist_id = artist_id;
    }
}
