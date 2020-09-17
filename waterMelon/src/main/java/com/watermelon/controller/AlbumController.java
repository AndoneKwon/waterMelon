package com.watermelon.controller;

import com.watermelon.domain.album.Album;
import com.watermelon.dto.album.AlbumResponseDto;
import com.watermelon.dto.album.AlbumUpdateRequestDto;
import com.watermelon.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AlbumController {

    private final AlbumService albumService;

    // 앨범 개별 조회
    @GetMapping("/v1/albums/{id}")
    public AlbumResponseDto read(@PathVariable Long id) {
        return albumService.read(id);
    }

    // 앨범 목록 조회
    @GetMapping("/v1/albums")
    public List<AlbumResponseDto> list() {
        return albumService.list();
    }

    // 앨범 수정
    @PatchMapping("/v1/albums/{id}")
    public Album update(@PathVariable Long id, @RequestBody AlbumUpdateRequestDto albumUpdateRequestDto) {
        return albumService.update(id, albumUpdateRequestDto);
    }

    // 앨범 삭제
    @DeleteMapping("/v1/albums/{id}")
    public String delete(@PathVariable Long id) {
        return albumService.delete(id);
    }
}
