package com.watermelon.domain.music;

import com.watermelon.domain.BaseTimeEntity;
import com.watermelon.domain.album.Album;
import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.domain.artist_music.ArtistMusic;
import com.watermelon.dto.music.MusicUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Music extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String lyrics;

    @Column(length = 100, nullable = false)
    private String composer;

    @Column(length = 100, nullable = false)
    private String songwriter;

    @Column(length = 100)
    private String arranger;

    @ColumnDefault("0")
    private Integer likes;
    @ColumnDefault("0")
    private Integer hits;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "release_date")
    private Date releaseDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "is_title", columnDefinition = "TINYINT")
    @ColumnDefault("0")
    private Boolean isTitle;

    @Lob
    private Blob thumbnail;

    private String genre;

    @OneToMany(mappedBy = "music")
    private List<ArtistMusic> artistMusics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private Album album;

    @Builder
    public Music(String title, String lyrics, String composer, String songwriter, String arranger, Integer likes,
                 Integer hits, Date releaseDate, Date deletedAt, Boolean isTitle, Blob thumbnail, String genre,
                 Album album) {
        this.title = title;
        this.lyrics = lyrics;
        this.composer = composer;
        this.songwriter = songwriter;
        this.arranger = arranger;
        this.likes = likes;
        this.hits = hits;
        this.releaseDate = releaseDate;
        this.deletedAt = deletedAt;
        this.isTitle = isTitle;
        this.thumbnail = thumbnail;
        this.genre = genre;
        this.album = album;
    }

    // 관계 연결
    public void addRelation(ArtistMusic artistMusic) {
        this.artistMusics = new ArrayList<>();
        this.artistMusics.add(artistMusic);
    }
    // 관계 해제
    public void removeRelation(ArtistMusic artistMusic) {
        this.artistMusics = new ArrayList<>();
        this.artistMusics.remove(artistMusic);
    }

    /**
     * 수정 기능
     * patch를 구현하기 위해 널 값을 체크하고 수정할 값이 들어있는
     * 필드만 수정 처리
     */
    public void update(MusicUpdateRequestDto requestDto, Album album) {
        if (requestDto.getTitle() != null) {
            this.title = requestDto.getTitle();
        }
        if (requestDto.getLyrics() != null) {
            this.lyrics = requestDto.getLyrics();
        }
        if (requestDto.getComposer() != null) {
            this.composer = requestDto.getComposer();
        }
        if (requestDto.getSongwriter() != null) {
            this.songwriter = requestDto.getSongwriter();
        }
        if (requestDto.getArranger() != null) {
            this.arranger = requestDto.getArranger();
        }
        if (requestDto.getLikes() != null) {
            this.likes = requestDto.getLikes();
        }
        if (requestDto.getHits() != null) {
            this.hits = requestDto.getHits();
        }
        if (requestDto.getReleaseDate() != null) {
            this.releaseDate = requestDto.getReleaseDate();
        }
        if (requestDto.getDeletedAt() != null) {
            this.deletedAt = requestDto.getDeletedAt();
        }
        if (requestDto.getIsTitle() != null) {
            this.isTitle = requestDto.getIsTitle();
        }
        if (requestDto.getThumbnail() != null) {
            this.thumbnail = requestDto.getThumbnail();
        }
        if (requestDto.getGenre() != null) {
            this.genre = requestDto.getGenre();
        }
        if (requestDto.getAlbumId() != null) {
            this.album = album;
        }
    }

    /**
     * 현재 시간을 Date 객체로 받아 와서 deleted_at 필드에 반영합니다.
     * deleted_at 필드를 확인해서 해당 레코드가 삭제된 레코드인지 분별합니다.
     * @param now
     */
    public void delete(Date now) {
        this.deletedAt = now;
    }
}
