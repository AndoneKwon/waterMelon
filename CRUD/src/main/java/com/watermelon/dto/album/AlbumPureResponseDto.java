package com.watermelon.dto.album;

import com.watermelon.domain.album.Album;
import lombok.Getter;

import java.util.Date;

/**
 * 관계 정보를 제외한 순수 정보를 담은 Dto
 */

@Getter
public class AlbumPureResponseDto {

    /**
     * 목적
     * 1. title :
     */

    private Long id;
    private String title;
    private String type;
    private Date publishDate;
    private String publisher;
    private String agency;
    private String information;
    private Date deletedAt;

    public AlbumPureResponseDto(Album entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.type = entity.getType();
        this.publishDate = entity.getPublishDate();
        this.publisher = entity.getPublisher();
        this.agency = entity.getAgency();
        this.information = entity.getInformation();
        this.deletedAt = entity.getDeletedAt();
    }
}
