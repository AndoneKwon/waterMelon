package com.watermelon.service;

import com.watermelon.domain.album.Album;
import com.watermelon.domain.album.AlbumRepository;
import com.watermelon.domain.artist.Artist;
import com.watermelon.dto.album.AlbumResponseDto;
import com.watermelon.dto.album.AlbumUpdateRequestDto;
import com.watermelon.dto.artist.ArtistResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private List<AlbumResponseDto> responseDtos;

    // 앨범 개별 조회
    @Transactional
    public AlbumResponseDto read(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아티스트입니다."));
        return new AlbumResponseDto(album);
    }

    @Transactional
    // 앨범 목록 조회
    public List<AlbumResponseDto> list() {
        List<Album> albums = albumRepository.findAll();

        responseDtos = new ArrayList<>();
        for (Album album : albums) {
            responseDtos.add(new AlbumResponseDto(album));
        }

        return responseDtos;
    }

    // 앨범 수정
    @Transactional
    public Album update(Long id, AlbumUpdateRequestDto albumUpdateRequestDto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("수정할 앨범이 존재하지 않습니다."));

        album.update(albumUpdateRequestDto);

        return album;
    }

    // 앨범 삭제
    @Transactional
    public String delete(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 앨범이 존재하지 않습니다."));

        Date now = new Date();
        album.delete(now);

        return "삭제되었습니다.";
    }
}
