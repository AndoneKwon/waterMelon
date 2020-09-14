package com.watermelon.domain.artist;

import com.watermelon.domain.BaseTimeEntity;
import com.watermelon.domain.album.Album;
import com.watermelon.domain.artist_album.ArtistAlbum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Artist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String information;
    private String fan_club;

    @Column(length = 30)
    private String activity;
    private String genre;

    @Column(length = 100)
    private String nationality;
    private String agency;

    private Boolean is_group;
    private Boolean is_concoction;

    private Date debut;
    private Date deleted_at;

    @OneToMany(mappedBy = "artist")
    private List<ArtistAlbum> artistAlbums;

    @Builder
    public Artist(String name, String information, String fan_club, String activity, String genre,
                  String nationality, String agency, Boolean is_concoction, Boolean is_group, Date debut, Date deleted_at) {
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
        this.deleted_at = deleted_at;
    }

    /**
     * 수정 기능
     * patch를 구현하기 위해 널 값을 체크하고 수정할 값이 들어있는
     * 필드만 수정 처리
     */
//    public void update(ArtistUpdateRequestDto requestDto) {
//        if (requestDto.getTitle() != null) {
//            this.title = requestDto.getTitle();
//        }
//        if (requestDto.getType() != null) {
//            this.type = requestDto.getType();
//        }
//        if (requestDto.getPublish_date() != null) {
//            this.publish_date = requestDto.getPublish_date();
//        }
//        if (requestDto.getPublisher() != null) {
//            this.publisher = requestDto.getPublisher();
//        }
//        if (requestDto.getAgency() != null) {
//            this.agency = requestDto.getAgency();
//        }
//        if (requestDto.getInformation() != null) {
//            this.information = requestDto.getInformation();
//        }
//    }

    // 삭제 기능
    public void delete(Date now) {
        this.deleted_at = now;
    }

}
