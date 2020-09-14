package com.watermelon.dto.album;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class AlbumUpdateRequestDto {

    private String title;
    private String type;
    private Date publish_date;
    private String publisher;
    private String agency;
    private String information;

    @Builder
    public AlbumUpdateRequestDto(String title, String type, Date publish_date,
                                 String publisher, String agency, String information) {
        this.title = title;
        this.type = type;
        this.publish_date = publish_date;
        this.publisher = publisher;
        this.agency = agency;
        this.information = information;
    }
}
