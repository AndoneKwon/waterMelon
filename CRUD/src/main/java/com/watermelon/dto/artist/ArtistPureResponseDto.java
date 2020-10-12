package com.watermelon.dto.artist;

import com.watermelon.domain.artist.Artist;
import lombok.Getter;

import java.util.Date;

/**
 * 관계 정보를 제외한 순수 정보를 담은 Dto
 */

@Getter
public class ArtistPureResponseDto {

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

    public ArtistPureResponseDto(Artist entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.information = entity.getInformation();
        this.fanClub = entity.getFanClub();
        this.activity = entity.getActivity();
        this.genre = entity.getGenre();
        this.nationality = entity.getNationality();
        this.agency = entity.getAgency();
        this.gender = entity.getGender();
        this.type = entity.getType();
        this.debutMusic = entity.getDebutMusic();
        this.isConcoction = entity.getIsConcoction();
        this.isGroup = entity.getIsGroup();
        this.debut = entity.getDebut();
        this.deletedAt = entity.getDeletedAt();
    }
}
