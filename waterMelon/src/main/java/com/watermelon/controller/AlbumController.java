package com.watermelon.controller;

import com.watermelon.domain.album.Album;
import com.watermelon.dto.album.AlbumReadResponseDto;
import com.watermelon.dto.album.AlbumUpdateRequestDto;
import com.watermelon.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AlbumController {

    private final AlbumService albumService;

    // 앨범 개별 조회
    @GetMapping("/v1/albums/{id}")
    public AlbumReadResponseDto read(@PathVariable Long id) {
        return albumService.read(id);
    }

    // 앨범 목록 조회
    @GetMapping("/v1/albums")
    public List<AlbumReadResponseDto> list() {
        return albumService.list();
    }

    // 앨범 수정
    @PatchMapping("/v1/albums/{id}")
    public AlbumReadResponseDto update(@PathVariable Long id, @RequestBody AlbumUpdateRequestDto requestDto) {
        return albumService.update(id, requestDto);
    }

    // 앨범 삭제
    @DeleteMapping("/v1/albums/{id}")
    public String delete(@PathVariable Long id) {
        return albumService.delete(id);
    }
}
