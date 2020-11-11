package com.watermelon.domain.music;

import com.watermelon.domain.BaseTimeEntity;
import com.watermelon.domain.album.Album;
import com.watermelon.domain.artist_music.ArtistMusic;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Blob;
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

//    /**
//     * 수정 기능
//     * patch를 구현하기 위해 널 값을 체크하고 수정할 값이 들어있는
//     * 필드만 수정 처리
//     */
//    public void update(ArtistUpdateRequestDto requestDto, List<Album> albums, Artist group) {
//        if (requestDto.getName() != null) {
//            this.name = requestDto.getName();
//        }
//        // 앨범 관계를 리셋하고 다시 매핑합니다
//        if (requestDto.getAlbum_id_list() != null) {
//            this.artistAlbums.clear();
//            for (Album album : albums) {
//                ArtistAlbum artistAlbum = ArtistAlbum.builder()
//                        .album(album)
//                        .build();
//                this.artistAlbums.add(artistAlbum);
//            }
//        }
//    }
//
//    // 삭제 기능
//    public void delete(Date now) {
//        this.deletedAt = now;
//    }
}
