package com.watermelon.service;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.album.AlbumRepository;
import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.artist.ArtistRepository;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.domain.artist_album.ArtistAlbumRepository;
import com.watermelon.domain.artist_music.ArtistMusic;
import com.watermelon.domain.artist_music.ArtistMusicRepository;
import com.watermelon.domain.music.Music;
import com.watermelon.domain.music.MusicRepository;
import com.watermelon.dto.artist.ArtistReadResponseDto;
import com.watermelon.dto.artist.ArtistUpdateRelationRequestDto;
import com.watermelon.dto.artist.ArtistUpdateRequestDto;
import com.watermelon.dto.music.MusicReadResponseDto;
import com.watermelon.dto.music.MusicUpdateRelationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final MusicRepository musicRepository;
    private final ArtistAlbumRepository artistAlbumRepository;
    private final ArtistMusicRepository artistMusicRepository;
    private List<ArtistReadResponseDto> responseDtos;

    // 아티스트 개별 조회
    @Transactional
    public ArtistReadResponseDto read(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));
        return new ArtistReadResponseDto(artist);
    }

    // 아티스트 목록 조회
    @Transactional
    public List<ArtistReadResponseDto> list(String keyword) {
        // 키워드 기반의 아티스트 이름으로 찾기
        List<Artist> artists = (keyword != null)
                ? artistRepository.findByNameContaining(keyword)
                : artistRepository.findAll();

        responseDtos = new ArrayList<>();
        for (Artist artist : artists) {
            responseDtos.add(new ArtistReadResponseDto(artist));
        }

        return responseDtos;
    }

    // 아티스트 수정
    @Transactional
    public ArtistReadResponseDto update(Long id, ArtistUpdateRequestDto requestDto) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));
        Artist group = new Artist();

        if (requestDto.getArtistId() != null) {
            group = artistRepository.findById(requestDto.getArtistId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));
        }

        artist.update(requestDto, group);

        return new ArtistReadResponseDto(artist);
    }

    // 아티스트 - 앨범 관계 추가
    @Transactional
    public ArtistReadResponseDto updateRelationAlbumAdd(Long id, ArtistUpdateRelationRequestDto requestDto) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));
        List<Album> albums;

        // 아티스트에 앨범을 새로 연결합니다.
        albums = albumRepository.findAllById(requestDto.getAlbumIdList());
        for (Album album : albums) {
            artistAlbumRepository.save(ArtistAlbum.builder()
                    .artist(artist)
                    .album(album)
                    .build()
            );
        }

        return new ArtistReadResponseDto(artist);
    }

    // 아티스트 - 앨범 관계 삭제
    @Transactional
    public ArtistReadResponseDto updateRelationAlbumDelete(Long id, ArtistUpdateRelationRequestDto requestDto) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));
        List<Album> albums;

        // 아티스트에 연결된 앨범을 연결 해제합니다.
        albums = albumRepository.findAllById(requestDto.getAlbumIdList());
        for (Album album : albums) {
            ArtistAlbum artistAlbum = artistAlbumRepository.findByArtistAndAlbum(artist, album);
            Date now = new Date();
            artistAlbum.delete(now);
        }

        return new ArtistReadResponseDto(artist);
    }

    // 아티스트 - 음악 관계 추가
    @Transactional
    public ArtistReadResponseDto updateRelationMusicAdd(Long id, ArtistUpdateRelationRequestDto requestDto) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));
        List<Music> musics;

        // 아티스트에 음악을 새로 연결합니다.
        musics = musicRepository.findAllById(requestDto.getMusicIdList());
        for (Music music : musics) {
            artistMusicRepository.save(ArtistMusic.builder()
                    .artist(artist)
                    .music(music)
                    .build()
            );
        }

        return new ArtistReadResponseDto(artist);
    }

    // 아티스트 - 음악 관계 삭제
    @Transactional
    public ArtistReadResponseDto updateRelationMusicDelete(Long id, ArtistUpdateRelationRequestDto requestDto) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));
        List<Music> musics;

        // 아티스트에 연결된 음악을 연결 해제합니다.
        musics = musicRepository.findAllById(requestDto.getMusicIdList());
        for (Music music : musics) {
            ArtistMusic artistMusic = artistMusicRepository.findByMusicAndArtist(music, artist);
            Date now = new Date();
            artistMusic.delete(now);
        }

        return new ArtistReadResponseDto(artist);
    }

    // 아티스트 삭제
    @Transactional
    public String delete(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));

        Date now = new Date();
        artist.delete(now);

        return "삭제되었습니다.";
    }
}
