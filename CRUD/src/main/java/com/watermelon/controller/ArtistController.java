package com.watermelon.controller;

import com.watermelon.dto.artist.ArtistReadResponseDto;
import com.watermelon.dto.artist.ArtistUpdateRelationRequestDto;
import com.watermelon.dto.artist.ArtistUpdateRequestDto;
import com.watermelon.dto.music.MusicReadResponseDto;
import com.watermelon.dto.music.MusicUpdateRelationRequestDto;
import com.watermelon.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArtistController {

    private final ArtistService artistService;

    // 아티스트 개별 조회
    @GetMapping("/v1/artists/{id}")
    public ArtistReadResponseDto read(@PathVariable Long id) {
        return artistService.read(id);
    }

    // 아티스트 목록 조회
    @GetMapping("/v1/artists")
    public List<ArtistReadResponseDto> list(@RequestParam(value = "keyword", required = false) String keyword) {
        return artistService.list(keyword);
    }

    // 아티스트 수정
    @PatchMapping("/v1/artists/{id}")
    public ArtistReadResponseDto update(@PathVariable Long id, @RequestBody ArtistUpdateRequestDto requestDto) {
        return artistService.update(id, requestDto);
    }

    // 아티스트 - 앨범 관계 추가
    @PatchMapping("/v1/artists/album-add/{id}")
    public ArtistReadResponseDto updateRelationAlbumAdd(@PathVariable Long id, @RequestBody ArtistUpdateRelationRequestDto requestDto) {
        return artistService.updateRelationAlbumAdd(id, requestDto);
    }

    // 아티스트 - 앨범 관계 삭제
    @PatchMapping("/v1/artists/album-delete/{id}")
    public ArtistReadResponseDto updateRelationAlbumDelete(@PathVariable Long id, @RequestBody ArtistUpdateRelationRequestDto requestDto) {
        return artistService.updateRelationAlbumDelete(id, requestDto);
    }

    // 아티스트 - 음악 관계 추가
    @PatchMapping("/v1/artists/music-add/{id}")
    public ArtistReadResponseDto updateRelationMusicAdd(@PathVariable Long id, @RequestBody ArtistUpdateRelationRequestDto requestDto) {
        return artistService.updateRelationMusicAdd(id, requestDto);
    }

    // 아티스트 - 음악 관계 삭제
    @PatchMapping("/v1/artists/music-delete/{id}")
    public ArtistReadResponseDto updateRelationMusicDelete(@PathVariable Long id, @RequestBody ArtistUpdateRelationRequestDto requestDto) {
        return artistService.updateRelationMusicDelete(id, requestDto);
    }

    // 아티스트 삭제
    @DeleteMapping("/v1/artists/{id}")
    public String delete(@PathVariable Long id) {
        return artistService.delete(id);
    }
}
