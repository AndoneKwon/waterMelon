package com.watermelon.service;

import com.watermelon.domain.music.Music;
import com.watermelon.domain.music.MusicRepository;
import com.watermelon.dto.music.MusicReadResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MusicService {

    private final MusicRepository musicRepository;

    // 음악 개별 조회
    @Transactional
    public MusicReadResponseDto read(Long id) {
        Music music = musicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음악입니다."));
        return new MusicReadResponseDto(music);
    }
}
