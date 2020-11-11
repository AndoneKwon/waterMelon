package com.watermelon.service;

import com.watermelon.domain.artist.Artist;
import com.watermelon.domain.music.Music;
import com.watermelon.domain.music.MusicRepository;
import com.watermelon.dto.artist.ArtistReadResponseDto;
import com.watermelon.dto.music.MusicReadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MusicService {

    private final MusicRepository musicRepository;
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
        if (keyword != null) {
            List<Music> musics = musicRepository.findByTitleContaining(keyword);
            responseDtos = new ArrayList<>();
            for (Music music : musics) {
                responseDtos.add(new MusicReadResponseDto(music));
            }

            return responseDtos;
        }
        List<Music> musics = musicRepository.findAll();

        responseDtos = new ArrayList<>();
        for (Music music : musics) {
            responseDtos.add(new MusicReadResponseDto(music));
        }

        return responseDtos;
    }
}
