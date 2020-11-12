package com.watermelon.service;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.album.AlbumRepository;
import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.artist.ArtistRepository;
import com.watermelon.domain.music.Music;
import com.watermelon.domain.music.MusicRepository;
import com.watermelon.dto.artist.ArtistReadResponseDto;
import com.watermelon.dto.artist.ArtistUpdateRequestDto;
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
        if (keyword != null) {
            List<Artist> artists = artistRepository.findByNameContaining(keyword);
            responseDtos = new ArrayList<>();
            for (Artist artist : artists) {
                responseDtos.add(new ArtistReadResponseDto(artist));
            }

            return responseDtos;
        }
        List<Artist> artists = artistRepository.findAll();

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
        List<Album> albums = new ArrayList<>();
        Artist group = new Artist();
        List<Music> musics = new ArrayList<>();

        if (requestDto.getAlbumIdList() != null) {
            albums = albumRepository.findAllById(requestDto.getAlbumIdList());
        }
        if (requestDto.getArtistId() != null) {
            group = artistRepository.findById(requestDto.getArtistId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));
        }
        if (requestDto.getMusicIdList() != null) {
            musics = musicRepository.findAllById(requestDto.getMusicIdList());
        }

        artist.update(requestDto, albums, group, musics);

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
