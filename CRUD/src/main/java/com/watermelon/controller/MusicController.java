package com.watermelon.controller;

import com.watermelon.domain.music.Music;
import com.watermelon.dto.music.MusicReadResponseDto;
import com.watermelon.dto.music.MusicUpdateRequestDto;
import com.watermelon.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MusicController {

    private final MusicService musicService;

    // 음악 개별 조회
    @GetMapping("/v1/musics/{id}")
    public MusicReadResponseDto read(@PathVariable Long id) {
        return musicService.read(id);
    }

    // 음악 목록 조회
    @GetMapping("/v1/musics")
    public List<MusicReadResponseDto> list(@RequestParam(value = "keyword", required = false) String keyword ) {
        return musicService.list(keyword);
    }

    // 음악 수정
    @PatchMapping("/v1/musics/{id}")
    public MusicReadResponseDto update(@PathVariable Long id, @RequestBody MusicUpdateRequestDto requestDto) {
        return musicService.update(id, requestDto);
    }

    // 음악 삭제
    @DeleteMapping("/v1/musics/{id}")
    public String delete(@PathVariable Long id) {
        return musicService.delete(id);
    }
}
