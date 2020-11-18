package com.watermelon.dto.artist;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class ArtistUpdateRequestDto {

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

    private Long artistId;

    @Builder
    public ArtistUpdateRequestDto(String name, String information, String fanClub, String activity, String genre,
                  String nationality, String agency, Boolean isConcoction, String gender, String type, String debutMusic,
                  Boolean isGroup, Date debut, Long artistId) {
        this.name = name;
        this.information = information;
        this.fanClub = fanClub;
        this.activity = activity;
        this.genre = genre;
        this.nationality = nationality;
        this.agency = agency;
        this.isConcoction = isConcoction;
        this.isGroup = isGroup;
        this.gender = gender;
        this.type = type;
        this.debutMusic = debutMusic;
        this.debut = debut;
        this.artistId = artistId;
    }
}
