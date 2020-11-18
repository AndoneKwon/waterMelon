package com.watermelon.controller;

import com.watermelon.domain.album.Album;
import com.watermelon.dto.album.AlbumReadResponseDto;
import com.watermelon.dto.album.AlbumUpdateRelationRequestDto;
import com.watermelon.dto.album.AlbumUpdateRequestDto;
import com.watermelon.dto.artist.ArtistReadResponseDto;
import com.watermelon.dto.artist.ArtistUpdateRelationRequestDto;
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
    public List<AlbumReadResponseDto> list(@RequestParam(value = "keyword", required = false) String keyword) {
        return albumService.list(keyword);
    }

    // 앨범 수정
    @PatchMapping("/v1/albums/{id}")
    public AlbumReadResponseDto update(@PathVariable Long id, @RequestBody AlbumUpdateRequestDto requestDto) {
        return albumService.update(id, requestDto);
    }

    // 앨범 - 아티스트 관계 추가
    @PatchMapping("/v1/albums/artist-add/{id}")
    public AlbumReadResponseDto updateRelationArtistAdd(@PathVariable Long id, @RequestBody AlbumUpdateRelationRequestDto requestDto) {
        return albumService.updateRelationArtistAdd(id, requestDto);
    }

    // 앨범 - 아티스트 관계 삭제
    @PatchMapping("/v1/albums/artist-delete/{id}")
    public AlbumReadResponseDto updateRelationArtistDelete(@PathVariable Long id, @RequestBody AlbumUpdateRelationRequestDto requestDto) {
        return albumService.updateRelationArtistDelete(id, requestDto);
    }

    // 앨범 삭제
    @DeleteMapping("/v1/albums/{id}")
    public String delete(@PathVariable Long id) {
        return albumService.delete(id);
    }
}
