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
    private Date publish_date;
    private String publisher;
    private String agency;
    private String information;

    private List<Long> artist_id_list;

    @Builder
    public AlbumUpdateRequestDto(String title, String type, Date publish_date,
                                 String publisher, String agency, String information, List<Long> artist_id_list) {
        this.title = title;
        this.type = type;
        this.publish_date = publish_date;
        this.publisher = publisher;
        this.agency = agency;
        this.information = information;
        this.artist_id_list = artist_id_list;
    }
}
