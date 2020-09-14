package com.watermelon.dto.album;

import com.watermelon.domain.album.Album;
import lombok.Getter;

import java.util.Date;

@Getter
public class AlbumResponseDto {

    private Long id;
    private String title;
    private String type;
    private Date publish_date;
    private String publisher;
    private String agency;
    private String information;
    private Date deleted_at;

    public AlbumResponseDto(Album entity) {
        this.title = entity.getTitle();
        this.type = entity.getType();
        this.publish_date = entity.getPublish_date();
        this.publisher = entity.getPublisher();
        this.agency = entity.getAgency();
        this.information = entity.getInformation();
        this.deleted_at = entity.getDeleted_at();
    }
}
