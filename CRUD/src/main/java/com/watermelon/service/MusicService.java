package com.watermelon.service;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.album.AlbumRepository;
import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.artist.ArtistRepository;
import com.watermelon.domain.artist_music.ArtistMusic;
import com.watermelon.domain.artist_music.ArtistMusicRepository;
import com.watermelon.domain.music.Music;
import com.watermelon.domain.music.MusicRepository;
import com.watermelon.dto.artist.ArtistReadResponseDto;
import com.watermelon.dto.music.MusicReadResponseDto;
import com.watermelon.dto.music.MusicUpdateRelationRequestDto;
import com.watermelon.dto.music.MusicUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MusicService {

    private final MusicRepository musicRepository;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final ArtistMusicRepository artistMusicRepository;

    private List<MusicReadResponseDto> responseDtos;

    // 음악 개별 조회
    @Transactional
    public MusicReadResponseDto read(Long id) {
        Music music = musicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음악입니다."));
        return new MusicReadResponseDto(music);
    }

    // 음악 목록 조회
    @Transactional
    public List<MusicReadResponseDto> list(String keyword) {
        // 키워드 기반의 음악 제목으로 찾기
        List<Music> musics = (keyword != null)
                ? musicRepository.findByTitleContaining(keyword)
                : musicRepository.findAll();

        responseDtos = new ArrayList<>();
        for (Music music : musics) {
            responseDtos.add(new MusicReadResponseDto(music));
        }

        return responseDtos;
    }

    // 음악 수정
    @Transactional
    public MusicReadResponseDto update(Long id, MusicUpdateRequestDto requestDto) {
        Music music = musicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음악입니다."));
        Album album = new Album();

        if (requestDto.getAlbumId() != null) {
            album = albumRepository.findById(requestDto.getAlbumId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 앨범입니다."));
        }

        music.update(requestDto, album);

        return new MusicReadResponseDto(music);
    }

    // 음악 - 아티스트 관계 추가
    @Transactional
    public MusicReadResponseDto updateRelationAdd(Long id, MusicUpdateRelationRequestDto requestDto) {
        Music music = musicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음악입니다."));
        List<Artist> artists;

        // 음악에 아티스트를 추가합니다.
        artists = artistRepository.findAllById(requestDto.getArtistIdList());
        for (Artist artist : artists) {
            artistMusicRepository.save(ArtistMusic.builder()
                    .music(music)
                    .artist(artist)
                    .build()
            );
        }

        return new MusicReadResponseDto(music);
    }

    // 음악 - 아티스트 관계 삭제
    @Transactional
    public MusicReadResponseDto updateRelationDelete(Long id, MusicUpdateRelationRequestDto requestDto) {
        Music music = musicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음악입니다."));
        List<Artist> artists;

        // 음악에 연결된 아티스트를 삭제합니다.
        artists = artistRepository.findAllById(requestDto.getArtistIdList());
        for (Artist artist : artists) {
            ArtistMusic artistMusic = artistMusicRepository.findByMusicAndArtist(music, artist);
            Date now = new Date();
            artistMusic.delete(now);
        }

        return new MusicReadResponseDto(music);
    }

    // 음악 삭제
    @Transactional
    public String delete(Long id) {
        Music music = musicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음악입니다."));

        Date now = new Date();
        music.delete(now);

        return "삭제가 완료되었습니다.";
    }
}
