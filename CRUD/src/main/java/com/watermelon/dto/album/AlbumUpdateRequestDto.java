package com.watermelon.dto.album;

import com.watermelon.domain.artist.Artist;
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

    private List<Long> artistIdList;
    private List<Long> musicIdList;

    @Builder
    public AlbumUpdateRequestDto(String title, String type, Date publishDate, String publisher, String agency,
                                 String information, List<Long> artistIdList, List<Long> musicIdList) {
        this.title = title;
        this.type = type;
        this.publishDate = publishDate;
        this.publisher = publisher;
        this.agency = agency;
        this.information = information;
        this.artistIdList = artistIdList;
        this.musicIdList = musicIdList;
    }
}
