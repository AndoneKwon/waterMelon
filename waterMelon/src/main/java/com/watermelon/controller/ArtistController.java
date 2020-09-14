package com.watermelon.controller;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.artist.Artist;
import com.watermelon.dto.artist.ArtistResponseDto;
import com.watermelon.service.ArtistService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ArtistController {

    private final ArtistService artistService;

    // 아티스트 개별 조회
    @GetMapping("/v1/artists/{id}")
    public ArtistResponseDto read(@PathVariable Long id) {
        return artistService.read(id);
    }

    // 아티스트 목록 조회
    @GetMapping("/v1/artists")
    public List<ArtistResponseDto> list() {
        return artistService.list();
    }

    // 아티스트 수정

    // 아티스트 삭제
}
