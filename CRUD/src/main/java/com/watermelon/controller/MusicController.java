package com.watermelon.controller;

import com.watermelon.dto.music.MusicReadResponseDto;
import com.watermelon.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MusicController {

    private final MusicService musicService;

    // 음악 개별 조회
    @GetMapping("/v1/musics/{id}")
    public MusicReadResponseDto read(@PathVariable Long id) {
        return musicService.read(id);
    }
}
