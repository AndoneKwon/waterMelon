package com.watermelon.service;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.album.AlbumRepository;
import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.artist.ArtistRepository;
import com.watermelon.domain.artist_album.ArtistAlbum;
import com.watermelon.domain.artist_album.ArtistAlbumRepository;
import com.watermelon.domain.artist_music.ArtistMusic;
import com.watermelon.domain.music.Music;
import com.watermelon.domain.music.MusicRepository;
import com.watermelon.dto.album.AlbumReadResponseDto;
import com.watermelon.dto.album.AlbumUpdateRelationRequestDto;
import com.watermelon.dto.album.AlbumUpdateRequestDto;
import com.watermelon.dto.artist.ArtistReadResponseDto;
import com.watermelon.dto.artist.ArtistUpdateRelationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final MusicRepository musicRepository;
    private final ArtistAlbumRepository artistAlbumRepository;
    private List<AlbumReadResponseDto> responseDtos;

    // 앨범 개별 조회
    @Transactional
    public AlbumReadResponseDto read(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));
        return new AlbumReadResponseDto(album);
    }

    @Transactional
    // 앨범 목록 조회
    public List<AlbumReadResponseDto> list(String keyword) {
        List<Album> albums = (keyword != null)
                ? albumRepository.findByTitleContaining(keyword)
                : albumRepository.findAll();

        responseDtos = new ArrayList<>();
        for (Album album : albums) {
            responseDtos.add(new AlbumReadResponseDto(album));
        }

        return responseDtos;
    }

    // 앨범 수정
    @Transactional
    public AlbumReadResponseDto update(Long id, AlbumUpdateRequestDto requestDto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("수정할 앨범이 존재하지 않습니다."));

        album.update(requestDto);

        return new AlbumReadResponseDto(album);
    }

    // 아티스트 - 앨범 관계 추가
    @Transactional
    public AlbumReadResponseDto updateRelationArtistAdd(Long id, AlbumUpdateRelationRequestDto requestDto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 앨범입니다."));
        List<Artist> artists;

        // 앨범에 아티스트를 새로 연결합니다.
        artists = artistRepository.findAllById(requestDto.getArtistIdList());
        for (Artist artist : artists) {
            artistAlbumRepository.save(ArtistAlbum.builder()
                    .album(album)
                    .artist(artist)
                    .build()
            );
        }

        return new AlbumReadResponseDto(album);
    }

    // 아티스트 - 앨범 관계 삭제
    @Transactional
    public AlbumReadResponseDto updateRelationArtistDelete(Long id, AlbumUpdateRelationRequestDto requestDto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 앨범입니다."));
        List<Artist> artists;

        // 앨범에 연결된 아티스트를 연결 해제합니다.
        artists = artistRepository.findAllById(requestDto.getArtistIdList());
        for (Artist artist : artists) {
            ArtistAlbum artistAlbum = artistAlbumRepository.findByArtistAndAlbum(artist, album);
            Date now = new Date();
            artistAlbum.delete(now);
        }

        return new AlbumReadResponseDto(album);
    }

    // 앨범 삭제
    @Transactional
    public String delete(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 앨범이 존재하지 않습니다."));

        Date now = new Date();
        album.delete(now);

        return "삭제되었습니다.";
    }
}
