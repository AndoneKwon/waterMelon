package com.watermelon.domain.artist;

import com.watermelon.domain.BaseTimeEntity;
import com.watermelon.domain.album.Album;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.domain.artist_music.ArtistMusic;
import com.watermelon.dto.artist.ArtistUpdateRequestDto;
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
public class Artist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String information;
    @Column(name = "fan_club", columnDefinition = "TEXT")
    private String fanClub;

    @Column(length = 30)
    private String activity;
    @Column(length = 30)
    private String genre;

    @Column(length = 100)
    private String nationality;
    @Column(length = 100)
    private String agency;

    @Column(name = "is_group", columnDefinition = "TINYINT")
    private Boolean isGroup;
    @Column(name = "is_concoction", columnDefinition = "TINYINT")
    private Boolean isConcoction;

    @Temporal(TemporalType.TIMESTAMP)
    private Date debut;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(length = 10)
    private String gender;
    @Column(columnDefinition = "TEXT")
    private String type;
    @Column(name = "debut_music", length = 100)
    private String debutMusic;


    @OneToMany(mappedBy = "artist")
    private List<ArtistAlbum> artistAlbums;

    @OneToMany(mappedBy = "artist")
    private List<ArtistMusic> artistMusics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private Artist group;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Artist> members;

    @Builder
    public Artist(String name, String information, String fanClub, String activity, String genre,
                  String nationality, String agency, Boolean isConcoction,
                  Boolean isGroup, Date debut, String debutMusic, String gender, String type,
                  Date deletedAt, Artist group) {
        this.name = name;
        this.information = information;
        this.fanClub = fanClub;
        this.activity = activity;
        this.genre = genre;
        this.nationality = nationality;
        this.agency = agency;
        this.isConcoction = isConcoction;
        this.isGroup = isGroup;
        this.debut = debut;
        this.debutMusic = debutMusic;
        this.gender = gender;
        this.type = type;
        this.deletedAt = deletedAt;
        this.group = group;
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
    public void update(ArtistUpdateRequestDto requestDto, List<Album> albums, Artist group) {
        if (requestDto.getName() != null) {
            this.name = requestDto.getName();
        }
        if (requestDto.getInformation() != null) {
            this.information = requestDto.getInformation();
        }
        if (requestDto.getFanClub() != null) {
            this.fanClub = requestDto.getFanClub();
        }
        if (requestDto.getActivity() != null) {
            this.activity = requestDto.getActivity();
        }
        if (requestDto.getGenre() != null) {
            this.genre = requestDto.getGenre();
        }
        if (requestDto.getNationality() != null) {
            this.nationality = requestDto.getNationality();
        }
        if (requestDto.getAgency() != null) {
            this.agency = requestDto.getAgency();
        }
        if (requestDto.getGender() != null) {
            this.gender = requestDto.getGender();
        }
        if (requestDto.getType() != null) {
            this.type = requestDto.getType();
        }
        if (requestDto.getDebutMusic() != null) {
            this.debutMusic = requestDto.getDebutMusic();
        }
        if (requestDto.getIsGroup() != null) {
            this.isGroup = requestDto.getIsGroup();
        }
        if (requestDto.getIsConcoction() != null) {
            this.isConcoction = requestDto.getIsConcoction();
        }
        if (requestDto.getDebut() != null) {
            this.debut = requestDto.getDebut();
        }
        // 앨범 관계를 리셋하고 다시 매핑합니다
        if (requestDto.getAlbum_id_list() != null) {
            this.artistAlbums.clear();
            for (Album album : albums) {
                ArtistAlbum artistAlbum = ArtistAlbum.builder()
                        .album(album)
                        .build();
                this.artistAlbums.add(artistAlbum);
            }
        }
        // 아티스트는 자신의 아티스트 그룹을 설정할 수 있습니다
        if (requestDto.getArtist_id() != null) {
            this.group = group;
        }
    }

    // 삭제 기능
    public void delete(Date now) {
        this.deletedAt = now;
    }
}
