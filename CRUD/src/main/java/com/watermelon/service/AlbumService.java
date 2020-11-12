package com.watermelon.service;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.album.AlbumRepository;
import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.artist.ArtistRepository;
import com.watermelon.domain.music.Music;
import com.watermelon.domain.music.MusicRepository;
import com.watermelon.dto.album.AlbumReadResponseDto;
import com.watermelon.dto.album.AlbumUpdateRequestDto;
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
    public List<AlbumReadResponseDto> list() {
        List<Album> albums = albumRepository.findAll();

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

        List<Artist> artists = new ArrayList<>();
        List<Music> musics = new ArrayList<>();

        if (requestDto.getArtistIdList() != null) {
            artists = artistRepository.findAllById(requestDto.getArtistIdList());
        }
        if (requestDto.getMusicIdList() != null) {
            musics = musicRepository.findAllById(requestDto.getMusicIdList());
        }

        album.update(requestDto, artists, musics);

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
