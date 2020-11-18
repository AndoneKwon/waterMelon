package com.watermelon.dto.album;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class AlbumUpdateRequestDto {

    private String title;
    private String type;
    private Date publishDate;
    private String publisher;
    private String agency;
    private String information;

    @Builder
    public AlbumUpdateRequestDto(String title, String type, Date publishDate, String publisher, String agency,
                                 String information) {
        this.title = title;
        this.type = type;
        this.publishDate = publishDate;
        this.publisher = publisher;
        this.agency = agency;
        this.information = information;
    }
}
