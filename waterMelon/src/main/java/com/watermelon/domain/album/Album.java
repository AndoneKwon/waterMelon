package com.watermelon.domain.album;

import com.watermelon.domain.BaseTimeEntity;
import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.dto.album.AlbumUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Album extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 20)
    private String type;

    private Date publish_date;
    private String publisher;
    private String agency;

    @Column(columnDefinition = "TEXT")
    private String information;

    private Date deleted_at;

    @OneToMany(mappedBy = "album")
    private List<ArtistAlbum> artistAlbums;

    @Builder
    public Album(String title, String type, Date publish_date,
                 String publisher, String agency, String information, Date deleted_at) {
        this.title = title;
        this.type = type;
        this.publish_date = publish_date;
        this.publisher = publisher;
        this.agency = agency;
        this.information = information;
        this.deleted_at = deleted_at;
    }

    // 관계 연결
    public void addRelation(ArtistAlbum artistAlbum) {
        this.artistAlbums = new ArrayList<>();
        this.artistAlbums.add(artistAlbum);
    }
    // 관계 해제
    public void removeRelation(ArtistAlbum artistAlbum) {
        this.artistAlbums = new ArrayList<>();
        this.artistAlbums.remove(artistAlbum);
    }

    /**
     * 수정 기능
     * patch를 구현하기 위해 널 값을 체크하고 수정할 값이 들어있는
     * 필드만 수정 처리
     */
    public void update(AlbumUpdateRequestDto requestDto) {
        if (requestDto.getTitle() != null) {
            this.title = requestDto.getTitle();
        }
        if (requestDto.getType() != null) {
            this.type = requestDto.getType();
        }
        if (requestDto.getPublish_date() != null) {
            this.publish_date = requestDto.getPublish_date();
        }
        if (requestDto.getPublisher() != null) {
            this.publisher = requestDto.getPublisher();
        }
        if (requestDto.getAgency() != null) {
            this.agency = requestDto.getAgency();
        }
        if (requestDto.getInformation() != null) {
            this.information = requestDto.getInformation();
        }
    }
    // 삭제 기능
    public void delete(Date now) {
        this.deleted_at = now;
    }

}
